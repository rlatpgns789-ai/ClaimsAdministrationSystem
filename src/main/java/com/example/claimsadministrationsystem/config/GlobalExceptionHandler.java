package com.example.claimsadministrationsystem.config;

import com.example.claimsadministrationsystem.common.dto.ErrorResponse;
import com.example.claimsadministrationsystem.common.error.InvalidProxyRequestStateException;
import com.example.claimsadministrationsystem.common.error.InvalidProxyRequestStatusTransitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidProxyRequestStatusTransitionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProxyRequestTransition(InvalidProxyRequestStatusTransitionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(InvalidProxyRequestStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProxyRequestState(InvalidProxyRequestStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(HttpStatus.CONFLICT, e.getMessage()));
    }

}
