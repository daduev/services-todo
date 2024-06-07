package org.home.dev.dto.user;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private Long id;
    private String username;
    private List<String> roles = new ArrayList<>();

    public UserDto(Long id, String username, String password, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
