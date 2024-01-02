package com.antecuic.realworld.User.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.antecuic.realworld.User.User;
import com.antecuic.realworld.User.dtos.CreateUserRequestDTO;
import com.antecuic.realworld.User.dtos.UserDTO;
import com.antecuic.realworld.User.mappers.UserDTOMapper;
import com.antecuic.realworld.shared.repositories.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    public UserServiceImpl(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    public UserDTO getUserByEmailOrUsername(String email, String usermane) {
        User user = userRepository.findByEmailOrUsername(email, usermane);
        return this.userDTOMapper.mapUserToDTO(user);
    }

    @Override
    public UserDTO registerNewUser(CreateUserRequestDTO newUser) {
        User user = new User(newUser.user().username(), newUser.user().email(), newUser.user().password());

        if (userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with this email or username already exists.");
        }

        return this.userDTOMapper.mapUserToDTO(userRepository.save(user));

    }

    @Override
    public UserDTO getUserById(long userId) {
        User myUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return this.userDTOMapper.mapUserToDTO(myUser);
    }

}
