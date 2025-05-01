package com.filmFlix.project_filmFlix.services;

import com.filmFlix.project_filmFlix.Exceptions.DuplacationEntityException;
import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.dtos.authDtos.SingUpRequestDto;
import com.filmFlix.project_filmFlix.dtos.authDtos.PasswordRecoverDtos.newPasswordDto;
import com.filmFlix.project_filmFlix.entities.PasswordRecover;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.repositories.RecoverRepository;
import com.filmFlix.project_filmFlix.repositories.RoleRepository;
import com.filmFlix.project_filmFlix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private RecoverRepository recoverRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Value("${email.password-recover.uri}")
    private String passwordRecoverUri;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.getByEmail(email);
    }

    @Transactional
    public User singUp(SingUpRequestDto request){
        try {
            User newUser = new User();

            newUser.setEmail(request.email());
            newUser.setRole(roleRepository.findById(1L).orElseThrow(() -> new ResourcesNotFoundException("peril nao encontrado")));
            newUser.setName(request.name());
            newUser.setPassword(passwordEncoder.encode(request.password()));

            return  repository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new DuplacationEntityException("dados invalidos ");
        }

    }
    @Transactional
    public User findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("usuario nao encontrado"));
    }

    @Transactional
    public void recoverPassword(String email) {
        var user = repository.getByEmail(email).getUsername();
        if(user == null){
            throw new ResourcesNotFoundException("email nao encontrado");
        }
        PasswordRecover entity = new PasswordRecover();

        entity.setEmail(email);
        entity.setToken(UUID.randomUUID().toString());

        recoverRepository.save(entity);

        emailService.sendEmail(email, "recuperar senha", passwordRecoverUri + "/" + entity.getToken() );

    }

    @Transactional
    public void resetPassword(newPasswordDto request) {
        var result = recoverRepository.findByToken(request.token(), LocalDateTime.now()).orElseThrow(() -> new RuntimeException("token invalido"));
        var user =(User) repository.getByEmail(result.getEmail());
        user.setPassword(passwordEncoder.encode(request.password()));
        repository.save(user);

    }
}
