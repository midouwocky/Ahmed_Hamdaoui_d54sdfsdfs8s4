package com.anywr.ahmedtest.web.rest.errors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nonnull;

@RestControllerAdvice
public class GlobalExceptionHandler{
	
    @ExceptionHandler(LoginAlreadyUsedException.class)
    public ResponseEntity<String> handleException(LoginAlreadyUsedException ex) {
        return ResponseEntity.badRequest().body("login already exists");
    }
    
    @ExceptionHandler(BadRequestAlertException.class)
    public ResponseEntity<String> handleException(BadRequestAlertException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        ValidationResponse response = new ValidationResponse(fieldErrors);
        return ResponseEntity.badRequest().body(response);
    }
}

