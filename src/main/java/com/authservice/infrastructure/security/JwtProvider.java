package com.authservice.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.authservice.infrastructure.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;


@Component
public class JwtProvider {
    private final Algorithm algorithm;
    private final long jwtExpiration;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration}") long jwtExpiration) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.jwtExpiration = jwtExpiration;
    }

    public String generateToken(UserEntity user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withClaim("role", "USER")
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plusSeconds(jwtExpiration)))
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
