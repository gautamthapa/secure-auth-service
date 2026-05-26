package com.authservice.common.exception;

import org.springframework.http.HttpStatus;

public enum ServiceExceptionCodes {

    INTERNAL_ERROR(
            5000,
            "Internal server error",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    INVALID_REQUEST(
            4000,
            "Invalid request",
            HttpStatus.BAD_REQUEST
    ),

    INVALID_CREDENTIALS(
            4001,
            "Invalid username or password",
            HttpStatus.UNAUTHORIZED
    ),

    ACCESS_DENIED(
            4003,
            "Access denied",
            HttpStatus.FORBIDDEN
    ),

    RECORD_NOT_FOUND(
            4004,
            "Record not found",
            HttpStatus.NOT_FOUND
    ),

    ACCOUNT_LOCKED(
            4005,
            "Account locked due to multiple failed attempts",
            HttpStatus.LOCKED
    ),

    INVALID_REFRESH_TOKEN(
            4006,
            "Invalid refresh token",
            HttpStatus.UNAUTHORIZED
    ),

    REFRESH_TOKEN_EXPIRED(
            4007,
            "Refresh token expired",
            HttpStatus.UNAUTHORIZED
    ),

    FILE_UPLOAD_FAILED(
            4008,
            "File upload failed",
            HttpStatus.BAD_REQUEST
    );

    private final int code;

    private final String message;

    private final HttpStatus httpStatus;

    ServiceExceptionCodes(
            int code,
            String message,
            HttpStatus httpStatus
    ) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}