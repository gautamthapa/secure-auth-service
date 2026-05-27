package com.authservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoutRequest {
    private String refreshToken;
}
