package org.home.dev.dto.user;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
