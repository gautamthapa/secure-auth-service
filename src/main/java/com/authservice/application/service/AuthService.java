package com.authservice.application.service;

import com.authservice.application.dto.LoginRequest;
import com.authservice.application.dto.SignupRequest;
import com.authservice.common.response.ApiResponse;

public interface AuthService {
    ApiResponse signUp(SignupRequest request);

    ApiResponse login(LoginRequest request);
}
