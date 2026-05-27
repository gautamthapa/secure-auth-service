package com.authservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens", indexes = {
        @Index(name = "idx_refresh_tokens_user_id", columnList = "userId")
})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true, length = 512)
    private String refreshTokenHash;

    private String deviceId;

    private String ipAddress;

    @Column(length = 1000)
    private String userAgent;

    private Instant createdAt = Instant.now();

    private Instant lastUsedAt = Instant.now();

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private Boolean revoked;

    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }

    public boolean isActive() {
        return !revoked || !isExpired();
    }

}
