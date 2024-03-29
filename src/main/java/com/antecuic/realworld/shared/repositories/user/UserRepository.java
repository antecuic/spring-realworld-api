package com.antecuic.realworld.shared.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.antecuic.realworld.User.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailOrUsername(String email, String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
