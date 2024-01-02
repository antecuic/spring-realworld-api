package com.antecuic.realworld.shared.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.antecuic.realworld.User.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
    User findByEmailOrUsername(String email, String username);
}
