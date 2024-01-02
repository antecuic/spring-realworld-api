package com.antecuic.realworld.User.services;

import com.antecuic.realworld.User.dtos.CreateUserRequestDTO;
import com.antecuic.realworld.User.dtos.UserDTO;

public interface UserService {
    public UserDTO getUserByEmailOrUsername(String email, String usermane);

    public UserDTO registerNewUser(CreateUserRequestDTO newUser);

    public UserDTO getUserById(long userId);
}