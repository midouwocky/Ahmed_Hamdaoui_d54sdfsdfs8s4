package com.anywr.ahmedtest.web.rest.errors;

public class InvalidPasswordException extends Error {

    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super("Incorrect password");
    }
}
