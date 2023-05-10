package com.anywr.ahmedtest.web.rest.errors;

import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationResponse {
    private List<FieldError> errors;

    public ValidationResponse(List<FieldError> errors) {
        this.errors = errors;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }
}