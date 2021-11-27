package com.m2w.sispj.service;

import com.m2w.sispj.domain.User;
import com.m2w.sispj.repository.RefreshTokenRepository;
import com.m2w.sispj.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private final UserRepository userRepository;

    @Value("${sispj.security.secret}")
    private String secret;

    @Value("${sispj.security.timeout}")
    private long timeout;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(User user) {
        Date today = new Date();
        Date expiration = new Date(today.getTime() + timeout);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("profile", user.getProfile());
        claims.put("changePassword", user.isChangePassword());

        String token = Jwts.builder()
            .setIssuer("SISPJ")
            .setSubject(user.getId().toString())
            .setClaims(claims)
            .setIssuedAt(today)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();

        return "Bearer " + token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return (String)claims.get("email");
    }
}