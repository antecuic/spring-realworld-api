package com.antecuic.realworld.Auth;

import org.springframework.web.bind.annotation.RestController;

import com.antecuic.realworld.User.dtos.CreateUserRequestDTO;
import com.antecuic.realworld.User.dtos.UserDTO;
import com.antecuic.realworld.User.dtos.LoginCredentialsDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController()
@RequestMapping("/users")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping()
    public UserDTO registerNewUser(@RequestBody @Valid CreateUserRequestDTO user) {
        return authService.registerNewUser(user);
    }

    @PostMapping("login")
    public String login(@RequestBody LoginCredentialsDTO credentials) {
        return authService.authenticate(credentials);

    }

}
