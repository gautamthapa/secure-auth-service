package com.authservice.api.advice;

import com.authservice.common.exception.ApiServiceException;
import com.authservice.common.exception.ServiceExceptionCodes;
import com.authservice.common.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiServiceException.class)
    public ResponseEntity<ErrorResponse> handleApiServiceException(
            ApiServiceException ex,
            HttpServletRequest request
    ) {

        log.error("Exception caught in GlobalExceptionHandler handleApiServiceException ", ex);
        ServiceExceptionCodes error = ex.getErrorCode();

        ErrorResponse response = new ErrorResponse(
                error.getCode(),
                ex.getMessage(),
                ex.getDevMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(error.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request
    ) {

        log.error("Exception caught in GlobalExceptionHandler handleException", ex);

        ErrorResponse response = new ErrorResponse(
                ServiceExceptionCodes.INTERNAL_ERROR.getCode(),
                ServiceExceptionCodes.INTERNAL_ERROR.getMessage(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .internalServerError()
                .body(response);
    }
}