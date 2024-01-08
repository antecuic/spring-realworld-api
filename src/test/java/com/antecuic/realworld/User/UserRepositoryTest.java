package com.antecuic.realworld.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.antecuic.realworld.shared.repositories.user.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindUserByEmailOrUsername() {
        // given
        String username = "username1";
        String email = "username1@email.com";
        User user = User.builder().username(username).email(email).password("password").build();
        underTest.save(user);
        // when
        User userFoundByUsername = underTest.findByEmailOrUsername("wrongemail@mail.com", user.getUsername());
        User userFoundByEmail = underTest.findByEmailOrUsername(user.getEmail(), "wrong username");
        // then
        assertThat(user).isEqualTo(userFoundByUsername);
        assertThat(user).isEqualTo(userFoundByEmail);
    }

    @Test
    void itShouldNotFindUserWithWrongBothEmailAndUsername() {
        // given
        String username = "username1";
        String email = "username1@email.com";
        User user = User.builder().username(username).email(email).password("password").build();
        underTest.save(user);
        // when
        User userFound = underTest.findByEmailOrUsername("wrongemail@mail.com", "wrong username");
        // then
        assertThat(userFound).isNull();

    }

    @Test
    void itShouldFindUserByEmail() {
        // given
        String username = "username1";
        String email = "username1@email.com";
        User user = User.builder().username(username).email(email).password("password").build();
        underTest.save(user);
        // when
        Optional<User> userFound = underTest.findByEmail(user.getEmail());
        // then
        assertEquals(Optional.of(user), userFound);

    }

    @Test
    void itShouldFindUserByInvalidEmail() {
        // given
        String username = "username1";
        String email = "username1@email.com";
        User user = User.builder().username(username).email(email).password("password").build();
        underTest.save(user);
        // when
        Optional<User> userFound = underTest.findByEmail("invalid@mail.com");
        // then
        assertThat(userFound).isEmpty();

    }

}