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
        private final String secret = "123456"; // Chave secreta para criptografia
        private User user;
        @BeforeEach
        void setUp() {
            jwtService = new JwtService(); // Inicializa o serviço JwtService
            ReflectionTestUtils.setField(jwtService, "rsaPublicKey", secret); // Define a chave pública
            user = (User) EntitiesFactory.createUser(); // Cria um usuário de teste utilizando a fábrica
        }

        // Testa a criação de um token válido para o usuário
        @Test
        void createTokenShouldReturnValidToken() {
            TokenDto tokenDto = jwtService.createToken(user); // Cria o token para o usuário
            assertNotNull(tokenDto); // Verifica se o token não é nulo
            assertNotNull(tokenDto.token()); // Verifica se o valor do token não é nulo
            assertFalse(tokenDto.token().isEmpty()); // Verifica se o token não está vazio
        }

        // Testa se o metodo getSubject retorna o email correto a partir do token
        @Test
        void getSubjectShouldReturnCorrectEmail() {
            TokenDto tokenDto = jwtService.createToken(user); // Cria o token para o usuário
            String subject = jwtService.getSubject(tokenDto.token()); // Obtém o assunto (email) do token

            assertEquals(user.getEmail(), subject); // Verifica se o assunto do token corresponde ao email do usuário
        }

        // Testa se o metodo getSubject lança exceção quando o token é inválido
        @Test
        void getSubjectShouldThrowRunTimeExceptionForInvalidToken() {
            // Testa se o metodo getSubject lança exceção para um token inválido
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                jwtService.getSubject("invalidToken");
            });

            assertEquals("token invalido", exception.getMessage()); // Verifica a mensagem da exceção
        }

        // Testa se o metodo createToken lança exceção quando a chave é inválida
        @Test
        void createTokenShouldThrowIllegalArgumentExceptionWhenInvalidKey() {
            ReflectionTestUtils.setField(jwtService, "rsaPublicKey", null); // chave inválida
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                jwtService.createToken(user); // Testa a criação do token com chave inválida
            });
        }
    }

