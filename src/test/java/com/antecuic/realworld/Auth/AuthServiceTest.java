package com.antecuic.realworld.Auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.antecuic.realworld.User.User;
import com.antecuic.realworld.User.UserDTOMapper;
import com.antecuic.realworld.User.dtos.CreateUserRequestDTO;
import com.antecuic.realworld.config.JWTService;
import com.antecuic.realworld.shared.repositories.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

        @Mock
        private UserRepository userRepository;
        @Mock
        private UserDTOMapper mapper;
        @Mock
        private JWTService jwtService;

        private AuthServiceImpl underTest;

        @BeforeEach
        void setUp() {
                underTest = new AuthServiceImpl(userRepository, mapper, jwtService);
        }

        @Test
        void canRegisterNewUser() {
                // given
                User user = User.builder()
                                .username("Anasdvte")
                                .email("ante@asdmscail.com")
                                .password("mypass")
                                .build();

                CreateUserRequestDTO.User dto = new CreateUserRequestDTO.User(user.getUsername(), user.getEmail(),
                                user.getPassword());

                CreateUserRequestDTO dtoUser = new CreateUserRequestDTO(dto);

                // when
                given(userRepository.findByEmail(user.getEmail())).willReturn(null);
                underTest.registerNewUser(dtoUser);

                // then
                ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
                verify(userRepository).save(userArgumentCaptor.capture());
                User capturedUser = userArgumentCaptor.getValue();
                assertThat(capturedUser).isEqualTo(user);

        }

        @Test
        void canNotRegisterNewUser() {
                // given
                User user = User.builder()
                                .username("Ante")
                                .email("ante@msail.com")
                                .password("mypass")
                                .build();

                CreateUserRequestDTO.User dto = new CreateUserRequestDTO.User(user.getUsername(), user.getEmail(),
                                user.getPassword());

                CreateUserRequestDTO dtoUser = new CreateUserRequestDTO(dto);

                // when
                given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

                // then
                assertThatThrownBy(() -> underTest.registerNewUser(dtoUser))
                                .isInstanceOf(ResponseStatusException.class)
                                .hasMessageContaining("User with this email already exists.")
                                .extracting(e -> ((ResponseStatusException) e).getStatusCode().value())
                                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        }

}
