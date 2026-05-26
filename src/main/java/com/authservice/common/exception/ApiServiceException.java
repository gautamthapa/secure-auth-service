package com.authservice.common.exception;

public class ApiServiceException extends RuntimeException {

    private final ServiceExceptionCodes errorCode;

    private final String devMessage;

    public ApiServiceException(ServiceExceptionCodes errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.devMessage = null;
    }

    public ApiServiceException(
            ServiceExceptionCodes errorCode,
            String devMessage
    ) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.devMessage = devMessage;
    }

    public ApiServiceException(
            ServiceExceptionCodes errorCode,
            Throwable cause
    ) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.devMessage = cause.getMessage();
    }

    public ServiceExceptionCodes getErrorCode() {
        return errorCode;
    }

    public String getDevMessage() {
        return devMessage;
    }
}