package com.m2w.sispj.controller;

import com.m2w.sispj.dto.PasswordDTO;
import com.m2w.sispj.dto.ProfileDTO;
import com.m2w.sispj.dto.TokenDTO;
import com.m2w.sispj.dto.UserDTO;
import com.m2w.sispj.service.ProfileService;
import com.m2w.sispj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    private final UserService service;
    private final ProfileService profileService;

    public UserController(UserService service, ProfileService profileService) {
        this.service = service;
        this.profileService = profileService;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> list(@PageableDefault(size = Integer.MAX_VALUE) @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.list(pageable));
    }

    @GetMapping(value = "profile/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProfileDTO>> list() {
        return ResponseEntity.ok().body(profileService.list());
    }

    @GetMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> readById(@PathVariable Long userId) {
        return ResponseEntity.ok().body(service.findById(userId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PasswordDTO> create(@RequestBody UserDTO userDTO) {
        PasswordDTO response = service.create(userDTO);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "generate-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PasswordDTO> generateNewPassword(@RequestBody UserDTO userDTO) {
        PasswordDTO response = service.generateNewPassword(userDTO);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDTO> changePassword(@RequestBody PasswordDTO passwordDTO) {
        TokenDTO response = service.changePassword(passwordDTO);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        UserDTO response = service.update(userDTO);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable Long userId) {
        service.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
