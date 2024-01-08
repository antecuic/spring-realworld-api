package com.antecuic.realworld.User;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.antecuic.realworld.User.dtos.UserDTO;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return UserDTO.create(user.getUsername(), user.getEmail(), user.getBio());
    }

    public UserDTO mapUserToDTO(User user) {
        return this.apply(user);
    }

}