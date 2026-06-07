package com.devsaif.controller;

import com.devsaif.payload.dto.LoginDTO;
import com.devsaif.payload.dto.SignupDTO;
import com.devsaif.payload.response.AuthResponse;
import com.devsaif.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody SignupDTO req ) throws Exception{

        AuthResponse authResponse = authService.signup(req);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginDTO req ) throws Exception {

        AuthResponse authResponse = authService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/access-token/refresh-token/{refreshToken}")
    public ResponseEntity<AuthResponse> getAccessToken(
            @PathVariable String refreshToken ) throws Exception {

        AuthResponse authResponse = authService.getAccessTokenFromRefreshToken(refreshToken);
        return ResponseEntity.ok(authResponse);
    }
}
