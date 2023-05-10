package com.anywr.ahmedtest.web.rest.vm;

import com.anywr.ahmedtest.service.dto.UserDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the AdminUserDTO, which is meant to be used in the user management UI.
 */
public class RegisterUserDTO extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegisterUserDTO{" + super.toString() + "} ";
    }
}
