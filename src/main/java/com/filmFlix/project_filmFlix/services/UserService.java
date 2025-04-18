package com.filmFlix.project_filmFlix.services;

import com.filmFlix.project_filmFlix.Exceptions.DuplacationEntityException;
import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.dtos.authDtos.SingUpRequestDto;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.repositories.RoleRepository;
import com.filmFlix.project_filmFlix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


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
}
