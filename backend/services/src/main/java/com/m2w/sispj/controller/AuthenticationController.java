package com.m2w.sispj.controller;

import com.m2w.sispj.domain.RefreshToken;
import com.m2w.sispj.domain.User;
import com.m2w.sispj.dto.LoginDTO;
import com.m2w.sispj.dto.TokenDTO;
import com.m2w.sispj.dto.TokenRefreshDTO;
import com.m2w.sispj.dto.UserDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.error.exception.TokenRefreshException;
import com.m2w.sispj.service.RefreshTokenService;
import com.m2w.sispj.service.TokenService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            User user = (User) authentication.getPrincipal();
            String token = tokenService.generateToken(user);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

            TokenDTO tokenDTO = TokenDTO.builder().accessToken(token).refreshToken(refreshToken.getToken()).build();
            tokenDTO.setChangePassword(user.isChangePassword());

            return ResponseEntity.ok(tokenDTO);
        } catch (AuthenticationException e) {
            throw new SISPJException(SISPJExceptionDefinition.INCORRECT_USER_OR_PASSWORD);
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenDTO> refreshtoken(@Valid @RequestBody TokenRefreshDTO request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = tokenService.generateToken(user);
                    TokenDTO tokenDTO = TokenDTO.builder().accessToken(token).refreshToken(requestRefreshToken).build();
                    return ResponseEntity.ok(tokenDTO);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody UserDTO logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getId());
        return ResponseEntity.ok(null);
    }
}