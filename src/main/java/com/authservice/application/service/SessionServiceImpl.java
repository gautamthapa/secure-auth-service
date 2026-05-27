package com.authservice.application.service;

import com.authservice.application.dto.SessionResponse;
import com.authservice.common.SecurityUtils;
import com.authservice.common.exception.ApiServiceException;
import com.authservice.common.exception.ServiceExceptionCodes;
import com.authservice.common.response.ApiResponse;
import com.authservice.common.response.Payload;
import com.authservice.infrastructure.entity.RefreshTokenEntity;
import com.authservice.infrastructure.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public ApiResponse getMySessions() {
        Long userId = SecurityUtils.getCurrentUserId();

        if (userId == null) {
            throw new ApiServiceException(ServiceExceptionCodes.INVALID_CREDENTIALS);
        }

        List<RefreshTokenEntity> sessions = refreshTokenRepository.findByUserIdAndRevokedFalse(userId);
        List<SessionResponse> responses = sessions.stream().map(this::mapSessionToResponse).toList();

        return Payload.success(responses);
    }

    private SessionResponse mapSessionToResponse(RefreshTokenEntity refreshTokenEntity) {
        SessionResponse sessionResponse = new SessionResponse();
        sessionResponse.setSessionId(refreshTokenEntity.getId());
        sessionResponse.setDeviceId(refreshTokenEntity.getDeviceId());
        sessionResponse.setUserAgent(refreshTokenEntity.getUserAgent());
        sessionResponse.setIpAddress(refreshTokenEntity.getIpAddress());
        sessionResponse.setActive(refreshTokenEntity.isActive());
        sessionResponse.setCreatedAt(LocalDateTime.ofInstant(refreshTokenEntity.getCreatedAt(), ZoneId.systemDefault()));
        sessionResponse.setLastUsedAt(LocalDateTime.ofInstant(refreshTokenEntity.getLastUsedAt(), ZoneId.systemDefault()));
        return sessionResponse;
    }

    @Override
    public ApiResponse revokeSession(Long sessionId) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new ApiServiceException(ServiceExceptionCodes.INVALID_CREDENTIALS);
        }

        RefreshTokenEntity session = refreshTokenRepository.findByIdAndUserId(sessionId, userId).orElseThrow(() -> new ApiServiceException(ServiceExceptionCodes.INVALID_CREDENTIALS));
        session.setRevoked(true);
        refreshTokenRepository.save(session);

        return Payload.success("Session revoked successfully");
    }

    @Override
    public ApiResponse revokeAllSessions() {
        Long userId = SecurityUtils.getCurrentUserId();

        if (userId == null) {
            throw new ApiServiceException(ServiceExceptionCodes.INVALID_CREDENTIALS);
        }

        List<RefreshTokenEntity> sessions = refreshTokenRepository.findByUserIdAndRevokedFalse(userId);
        if (!sessions.isEmpty()) {
            sessions.forEach(session -> session.setRevoked(false));
            refreshTokenRepository.saveAll(sessions);
        }

        return Payload.success("All sessions are revoked successfully");
    }
}
