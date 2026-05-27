package com.authservice.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.authservice.infrastructure.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;


@Component
public class JwtProvider {
    private final Algorithm algorithm;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtProvider(RsaKeyProvider rsaKeyProvider, @Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration}") long accessExpiration,
                       @Value("${jwt.refresh-expiration}") long refreshExpiration) {
        this.algorithm = Algorithm.RSA256(
                (RSAPublicKey) rsaKeyProvider.getPublicKey(), (RSAPrivateKey) rsaKeyProvider.getPrivateKey()
        );
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateToken(UserEntity user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plusSeconds(accessExpiration)))
                .sign(algorithm);
    }

    public String generateRefreshToken(UserEntity user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withClaim("role", "USER")
                .withClaim("type", "REFRESH")
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plusSeconds(refreshExpiration)))
                .sign(algorithm);
    }

    public String extractEmailFromToken(String token) {
        return verifyToken(token).getSubject();
    }

    public Long extractUserId(String token) {
        return verifyToken(token)
                .getClaim("userId")
                .asLong();
    }

    public boolean validateToken(String token) {
        try {
            verifyToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private DecodedJWT verifyToken(String token) {
        return JWT.require(algorithm)
                .build().verify(token);
    }


}
