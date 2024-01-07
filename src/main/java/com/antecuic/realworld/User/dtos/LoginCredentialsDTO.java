package com.antecuic.realworld.User.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginCredentialsDTO(
                @JsonProperty("user") @Valid @NotNull(message = "User object is required") User user) {

        public record User(

                        @NotNull(message = "Email is required") @Email(message = "Not valid email format") String email,

                        @NotNull(message = "Password is required") String password) {
        }
}
