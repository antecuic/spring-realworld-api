package com.antecuic.realworld.User.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.antecuic.realworld.User.dtos.CreateUserRequestDTO;
import com.antecuic.realworld.User.dtos.UserDTO;
import com.antecuic.realworld.User.dtos.LoginCredentialsDTO;
import com.antecuic.realworld.User.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController()
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public UserDTO registerNewUser(@RequestBody @Valid CreateUserRequestDTO user) {
        return userService.registerNewUser(user);
    }

    @PostMapping("login")
    public String login(@RequestBody LoginCredentialsDTO credentials) {
        return userService.authenticate(credentials);

    }

    @GetMapping("/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmailOrUsername(email, null);
    }

}
