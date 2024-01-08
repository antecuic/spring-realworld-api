package com.antecuic.realworld.Auth;

import com.antecuic.realworld.User.dtos.CreateUserRequestDTO;
import com.antecuic.realworld.User.dtos.LoginCredentialsDTO;
import com.antecuic.realworld.User.dtos.UserDTO;

public interface AuthService {
    public UserDTO registerNewUser(CreateUserRequestDTO newUser);

    public String authenticate(LoginCredentialsDTO credetials);

}
