package com.filmFlix.project_filmFlix.service;

import com.filmFlix.project_filmFlix.dtos.authDtos.TokenDto;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.services.JwtService;
import com.filmFlix.project_filmFlix.util.EntitiesFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;
    private final String secret = "123456";
    private User user;

        @BeforeEach
        void setUp() {
            jwtService = new JwtService();
            ReflectionTestUtils.setField(jwtService, "rsaPublicKey", secret);
            user = (User) EntitiesFactory.createUser();
        }

        @Test
        void createTokenShouldReturnValidToken() {
            TokenDto tokenDto = jwtService.createToken(user);

            assertNotNull(tokenDto);
            assertNotNull(tokenDto.token());
            assertFalse(tokenDto.token().isEmpty());
        }

        @Test
        void getSubjectShouldReturnCorrectEmail() {
            TokenDto tokenDto = jwtService.createToken(user);
            String subject = jwtService.getSubject(tokenDto.token());

            assertEquals(user.getEmail(), subject);
        }

        @Test
        void getSubjectShouldThrowRunTimeExceptionForInvalidToken() {
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                jwtService.getSubject("invalidToken");
            });

            assertEquals("token invalido", exception.getMessage());
        }

        @Test
        void createTokenShouldThrowIllegalArgumentExceptionWhenInvalidKey() {
            ReflectionTestUtils.setField(jwtService, "rsaPublicKey", null); // chave inválida
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                jwtService.createToken(user);
            });
        }
    }

