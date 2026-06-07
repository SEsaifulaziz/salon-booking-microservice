package com.devsaif.controller;

import com.devsaif.payload.dto.SignupDTO;
import com.devsaif.payload.response.AuthResponse;
import com.devsaif.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
