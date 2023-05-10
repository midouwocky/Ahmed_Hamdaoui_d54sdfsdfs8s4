package com.anywr.ahmedtest.web.rest.errors;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class LoginAlreadyUsedException extends Error {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super("Login name already used!");
    }
}
