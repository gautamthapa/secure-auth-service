package com.authservice.api.controller;

import com.authservice.application.service.SessionService;
import com.authservice.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private final SessionService sessionService;

    @GetMapping
    public ApiResponse getMySessions() {
        return sessionService.getMySessions();
    }

    @DeleteMapping("/{id}")
    public ApiResponse revokeSession(@PathVariable Long id) {
        return sessionService.revokeSession(id);
    }

    @DeleteMapping("/all")
    public ApiResponse revokeAllSessions() {
        return sessionService.revokeAllSessions();
    }
}
