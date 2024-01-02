package com.antecuic.realworld.User.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(@JsonProperty("user") User user) {

    public static UserDTO create(String username, String email, String bio) {
        return new UserDTO(new User(username, email, bio));
    }

    public record User(
            String username,
            String email,
            String bio) {
    }

}
