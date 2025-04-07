package com.filmFlix.project_filmFlix.services;

import com.filmFlix.project_filmFlix.dtos.authDtos.SingUpRequestDto;
import com.filmFlix.project_filmFlix.dtos.authDtos.SingUpResponseDto;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.repositories.RoleRepository;
import com.filmFlix.project_filmFlix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.getByEmail(email);
    }

    @Transactional
    public SingUpResponseDto singUp(SingUpRequestDto request){
        User newUser = new User();

        newUser.setEmail(request.email());
        newUser.setRole(roleRepository.findById(1L).get());
        newUser.setPassword(request.password());

        var user =  repository.save(newUser);

        return new SingUpResponseDto(user.getId(), user.getEmail(), user.getUsername());
    }
    @Transactional
    public Optional<User> findById(Long id){
        return repository.findById(id);
    }
}
