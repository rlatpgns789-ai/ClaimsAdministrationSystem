package com.example.claimsadministrationsystem.config;

import com.example.claimsadministrationsystem.common.dto.ErrorResponse;
import com.example.claimsadministrationsystem.common.error.InvalidProxyRequestStatusTransitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidProxyRequestStatusTransitionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransition(InvalidProxyRequestStatusTransitionException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(HttpStatus.CONFLICT, e.getMessage()));
    }

}
