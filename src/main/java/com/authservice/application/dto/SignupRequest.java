package com.authservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {
    private String fullName;
    private String email;
    private String password;
}
