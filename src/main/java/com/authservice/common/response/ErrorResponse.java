package com.authservice.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements ApiResponse {

    private final boolean success = false;

    private int code;

    private String message;

    private String devMessage;

    private String path;

    private LocalDateTime timestamp;

    public ErrorResponse(
            int code,
            String message,
            String devMessage,
            String path
    ) {
        this.code = code;
        this.message = message;
        this.devMessage = devMessage;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}