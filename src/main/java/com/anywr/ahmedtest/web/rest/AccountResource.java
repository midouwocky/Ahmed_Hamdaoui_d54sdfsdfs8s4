package com.anywr.ahmedtest.web.rest;

import com.anywr.ahmedtest.service.UserService;
import com.anywr.ahmedtest.web.rest.errors.InvalidPasswordException;
import com.anywr.ahmedtest.web.rest.vm.RegisterUserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);


    private final UserService userService;


    public AccountResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param registerUserDTO the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        if (isPasswordLengthInvalid(registerUserDTO.getPassword())) {
            throw new InvalidPasswordException();
        }
        userService.registerUser(registerUserDTO, registerUserDTO.getPassword());
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < RegisterUserDTO.PASSWORD_MIN_LENGTH ||
            password.length() > RegisterUserDTO.PASSWORD_MAX_LENGTH
        );
    }
}
