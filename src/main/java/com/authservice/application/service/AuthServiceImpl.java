package com.authservice.application.service;

import com.authservice.application.dto.AuthResponse;
import com.authservice.application.dto.LoginRequest;
import com.authservice.application.dto.RefreshRequest;
import com.authservice.application.dto.SignupRequest;
import com.authservice.common.TokenHashUtil;
import com.authservice.common.exception.ApiServiceException;
import com.authservice.common.exception.ServiceExceptionCodes;
import com.authservice.common.response.ApiResponse;
import com.authservice.common.response.Payload;
import com.authservice.infrastructure.entity.RefreshTokenEntity;
import com.authservice.infrastructure.entity.UserEntity;
import com.authservice.infrastructure.repository.RefreshTokenRepository;
import com.authservice.infrastructure.repository.UserRepository;
import com.authservice.infrastructure.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Override
    public ApiResponse signUp(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiServiceException(ServiceExceptionCodes.INVALID_REQUEST, "Email already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.getFullName());
        userEntity.setEmail(request.getEmail());
        userEntity.setEnabled(true);
        userEntity.setLocked(false);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(userEntity);
        return Payload.success(null, "Account created successfully");
    }

    @Override
    public ApiResponse login(LoginRequest request, HttpServletRequest httpServletRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        UserEntity user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new ApiServiceException(ServiceExceptionCodes.INVALID_CREDENTIALS));

        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        saveUserSession(refreshToken, user.getId(), httpServletRequest);
        return Payload.success(new AuthResponse(accessToken, refreshToken), "Login successful");
    }

    private void saveUserSession(String refreshToken, Long userId, HttpServletRequest request) {
        RefreshTokenEntity session = new RefreshTokenEntity();
        session.setRefreshTokenHash(TokenHashUtil.hash(refreshToken));
        session.setUserId(userId);
        session.setRevoked(false);
        session.setExpiresAt(Instant.now().plus(refreshExpiration, ChronoUnit.MILLIS));

        setRefreshTokenDetails(session, request);
        refreshTokenRepository.save(session);

        session.setUserId(userId);
    }

    private void setRefreshTokenDetails(RefreshTokenEntity refreshToken, HttpServletRequest request) {
        String deviceId =
                request.getHeader("X-Device-Id");

        String userAgent =
                request.getHeader("User-Agent");

        String ip =
                request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        }

        refreshToken.setDeviceId(deviceId);
        refreshToken.setIpAddress(ip);
        refreshToken.setUserAgent(userAgent);
    }

    @Override
    public ApiResponse refreshToken(RefreshRequest refreshRequest, HttpServletRequest request) {

        String hashToken=TokenHashUtil.hash(refreshRequest.getRefreshToken());

        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByRefreshTokenHashAndRevokedFalse(hashToken)
                .orElseThrow(() -> new ApiServiceException(ServiceExceptionCodes.INVALID_REFRESH_TOKEN));

        if (refreshTokenEntity.isExpired()) {
            throw new ApiServiceException(ServiceExceptionCodes.REFRESH_TOKEN_EXPIRED);
        }

        Long userId = refreshTokenEntity.getUserId();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new ApiServiceException(ServiceExceptionCodes.INVALID_REQUEST));

        String newAccessToken = jwtProvider.generateToken(userEntity);
        String newRefreshToken = jwtProvider.generateRefreshToken(userEntity);

        refreshTokenEntity.setRevoked(true);
        refreshTokenRepository.save(refreshTokenEntity);

        RefreshTokenEntity newTokenEntity = new RefreshTokenEntity();
        newTokenEntity.setRefreshTokenHash(TokenHashUtil.hash(newRefreshToken));
        newTokenEntity.setUserId(userId);
        newTokenEntity.setRevoked(false);
        newTokenEntity.setExpiresAt(Instant.now().plus(refreshExpiration, ChronoUnit.MILLIS));

        setRefreshTokenDetails(newTokenEntity, request);
        refreshTokenRepository.save(newTokenEntity);

        AuthResponse authResponse = new AuthResponse(newAccessToken, newRefreshToken);
        return Payload.success(authResponse, "Token refreshed successfully");
    }

    @Override
    public ApiResponse logout(RefreshRequest refreshRequest) {
        String hashToken=TokenHashUtil.hash(refreshRequest.getRefreshToken());
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByRefreshTokenHashAndRevokedFalse(hashToken)
                .orElseThrow(() -> new ApiServiceException(ServiceExceptionCodes.INVALID_REFRESH_TOKEN));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        return Payload.success("Logout successful");
    }
}
