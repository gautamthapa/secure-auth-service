package com.authservice.api.controller;

import com.authservice.application.dto.LoginRequest;
import com.authservice.application.dto.SignupRequest;
import com.authservice.application.service.AuthService;
import com.authservice.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody SignupRequest signupRequest) {
        return authService.signUp(signupRequest);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
