package com.devsaif.service;


import com.devsaif.payload.dto.SignupDTO;
import com.devsaif.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password);
    AuthResponse signup(SignupDTO req);
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken);
}
