package com.antecuic.realworld.Auth;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.antecuic.realworld.User.User;
import com.antecuic.realworld.User.UserDTOMapper;
import com.antecuic.realworld.User.dtos.CreateUserRequestDTO;
import com.antecuic.realworld.User.dtos.LoginCredentialsDTO;
import com.antecuic.realworld.User.dtos.UserDTO;
import com.antecuic.realworld.config.JWTService;
import com.antecuic.realworld.shared.repositories.user.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final JWTService jwtService;

    public AuthServiceImpl(UserRepository userRepository, UserDTOMapper userDTOMapper, JWTService jwtService) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.jwtService = jwtService;
    }

    @Override
    public UserDTO registerNewUser(CreateUserRequestDTO newUser) {
        var user = User.builder()
                .username(newUser.user().username())
                .email(newUser.user().email())
                .password(newUser.user().password())
                .build();

        if (userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with this email or username already exists.");
        }

        return this.userDTOMapper.mapUserToDTO(userRepository.save(user));

    }

    @Override
    public String authenticate(LoginCredentialsDTO credentials) {
        User user = userRepository.findByEmail(credentials.user().email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return jwtService.generateToken(user);
    }

}
