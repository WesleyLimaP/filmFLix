package com.filmFlix.project_filmFlix.service;

import com.filmFlix.project_filmFlix.Exceptions.DuplacationEntityException;
import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.dtos.authDtos.SingUpRequestDto;
import com.filmFlix.project_filmFlix.entities.Role;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.enums.Authority;
import com.filmFlix.project_filmFlix.repositories.RoleRepository;
import com.filmFlix.project_filmFlix.repositories.UserRepository;
import com.filmFlix.project_filmFlix.services.AuthService;
import com.filmFlix.project_filmFlix.util.EntitiesFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    private AuthService service;
    @Mock
    private UserRepository repository;
    @Mock
    private RoleRepository roleRepository;
    private User user;
    private User invalidUser;
    private String validEmail;
    private String invalidEmail;
    private SingUpRequestDto requestDto;
    private Long validId;
    private Long invalidId;
    private Role role;
    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void SetUp(){
        role = new Role(Authority.ROLE_MEMBER);
        user = (User) EntitiesFactory.createUser();
        validEmail = "limap16@gmail.com";
        invalidEmail = "invalidEmail@gmail.com";
        requestDto = EntitiesFactory.createSingUpRequest();
        validId = 1L;
        invalidId = 2L;
        invalidUser = new User();



        when(roleRepository.findById(validId)).thenReturn(Optional.of(role));

        when(repository.findById(validId)).thenReturn(Optional.of(user));
        Mockito.doThrow(ResourcesNotFoundException.class).when(repository).findById(invalidId);


        when(passwordEncoder.encode(ArgumentMatchers.any())).thenReturn("senhaCodificada");

        when(repository.getByEmail(validEmail)).thenReturn(user);
        Mockito.doThrow(UsernameNotFoundException.class).when(repository).getByEmail(invalidEmail);

        when(repository.save(ArgumentMatchers.any())).thenReturn(user);


    }
    // Teste para carregar o usuário com email válido
    @Test
    void loadByUserNameShouldAnUserDetailsWhenValidEmail(){
        when(repository.getByEmail(validEmail)).thenReturn(user);
        var result =  service.loadUserByUsername(validEmail);
        Assertions.assertEquals(validEmail, result.getUsername());
        Assertions.assertEquals(User.class, result.getClass());
        Mockito.verify(repository, Mockito.times(1)).getByEmail(validEmail);
    }

    // Teste para lançar exceção quando o email for inválido
    @Test
    void loadByUserNameShouldThrowUsernameNotFoundExceptionWhenInvalidEmail(){
        Assertions.assertThrows( UsernameNotFoundException.class, () ->{
            service.loadUserByUsername(invalidEmail);
        });
        Mockito.verify(repository, Mockito.times(1)).getByEmail(invalidEmail);
    }

    // Teste para cadastrar o usuário com credenciais válidas
    @Test
    void singUpShouldReturnAnUserWhenValidCredentials(){
        var result =  service.singUp(requestDto);
        Assertions.assertEquals(validEmail, result.getUsername());
        Assertions.assertEquals(User.class, result.getClass());
        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any());
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(ArgumentMatchers.any());
        Assertions.assertEquals("senhaCodificada", passwordEncoder.encode("senha"));
    }

    // Teste para lançar exceção de duplicação quando as credenciais são inválidas
    @Test
    void singUpShouldThrowDuplicationExceptionWhenInvalidCredentials(){
        Mockito.doThrow(DuplacationEntityException.class).when(repository).save(any());
        Assertions.assertThrows( DuplacationEntityException.class, () ->{
            service.singUp(requestDto);
        });
    }

    // Teste para lançar exceção quando o papel (role) não for encontrado
    @Test
    void singUpShouldThrowResourceNotFoundExceptionWhenInvalidId(){
        Mockito.doThrow(ResourcesNotFoundException.class).when(roleRepository).findById(any());
        Assertions.assertThrows( ResourcesNotFoundException.class, () ->{
            service.singUp(requestDto);
        });
    }

    // Teste para retornar o usuário com um ID válido
    @Test
    void finByIdShouldReturnAnUserWhenValidId(){
        var result =  service.findById(validId);
        Assertions.assertEquals(validId, result.getId());
        Assertions.assertEquals(User.class, result.getClass());
        Mockito.verify(repository, Mockito.times(1)).findById(ArgumentMatchers.any());
    }

    // Teste para lançar exceção quando o ID for inválido
    @Test
    void finbByidShouldThrowResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourcesNotFoundException.class, () -> {
            service.findById(invalidId);
        });
    }

    }
