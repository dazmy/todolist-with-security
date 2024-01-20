package com.dazmy.todolist.security.controller;

import com.dazmy.todolist.security.model.response.CoreResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<CoreResponse<String>> constraintViolation(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CoreResponse.<String>builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .errors(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<CoreResponse<String>> responseStatus(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(
                        CoreResponse.<String>builder()
                                .status(exception.getStatusCode().value())
                                .errors(exception.getReason())
                                .build()
                );
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<CoreResponse<String>> authentication(AuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        CoreResponse.<String>builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .errors(exception.getMessage() + " : username or password invalid")
                                .build()
                );
    }

    // unsuccessful to catch the exception, maybe has a default, (level check is filter not at controller)
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<CoreResponse<String>> expiredToken(ExpiredJwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        CoreResponse.<String>builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .errors(exception.getMessage())
                        .build()
                );
    }
}
