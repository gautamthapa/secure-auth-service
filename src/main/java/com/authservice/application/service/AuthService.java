package com.authservice.application.service;

import com.authservice.application.dto.LoginRequest;
import com.authservice.application.dto.RefreshRequest;
import com.authservice.application.dto.SignupRequest;
import com.authservice.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    ApiResponse signUp(SignupRequest request);

    ApiResponse login(LoginRequest request, HttpServletRequest httpServletRequest);

    ApiResponse refreshToken(RefreshRequest refreshRequest, HttpServletRequest request);

    ApiResponse logout(RefreshRequest refreshRequest);
}
