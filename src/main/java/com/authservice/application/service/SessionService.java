package com.authservice.application.service;

import com.authservice.common.response.ApiResponse;

public interface SessionService {

    ApiResponse getMySessions();

    ApiResponse revokeSession(Long sessionId);

    ApiResponse revokeAllSessions();
}
