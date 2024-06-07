package org.home.dev.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.home.dev.dto.user.LoginDto;
import org.home.dev.dto.user.UserDto;
import org.home.dev.service.user.UsersServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UsersController {

    private final UsersServiceImpl usersServiceImpl;

    public UsersController(UsersServiceImpl usersServiceImpl) {
        this.usersServiceImpl = usersServiceImpl;
    }

    @PostMapping("/api/public/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto,
                                         HttpServletRequest request, HttpServletResponse response) {
        final UserDto login = usersServiceImpl.login(loginDto, request, response);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @PostMapping("/api/public/signup")
    public void signup(@Valid @RequestBody LoginDto loginDto) {
        usersServiceImpl.signup(loginDto);
    }

    @GetMapping("/api/private/users/current")
    public UserDto currentUserInfo() {
        return usersServiceImpl.getUserDto();
    }

    @GetMapping("/api/public/users")
    public List<UserDto> getUsers() {
        return usersServiceImpl.getUsers();
    }

}
