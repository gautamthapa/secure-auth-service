package com.authservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "login_attempts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginAttemptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Integer attempts;

    private Instant lastAttemptAt;

    private Boolean locked;
}
