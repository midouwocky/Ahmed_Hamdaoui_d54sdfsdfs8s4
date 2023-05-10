package com.anywr.ahmedtest.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginAlreadyUsedException.class)
    public ResponseEntity<String> handleException(LoginAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("login already exists");
    }
}
