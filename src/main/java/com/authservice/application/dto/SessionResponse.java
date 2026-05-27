package com.authservice.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SessionResponse {
    private Long sessionId;
    private String deviceId;
    private String userAgent;
    private String ipAddress;
    private LocalDateTime createdAt;
    private LocalDateTime lastUsedAt;
    private boolean active;
}
