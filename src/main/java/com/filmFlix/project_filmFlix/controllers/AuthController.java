package com.filmFlix.project_filmFlix.controllers;

import com.filmFlix.project_filmFlix.dtos.authDtos.LoginRequest;
import com.filmFlix.project_filmFlix.dtos.authDtos.SingUpRequestDto;
import com.filmFlix.project_filmFlix.dtos.authDtos.TokenDto;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.services.JwtService;
import com.filmFlix.project_filmFlix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userservice;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request){
        var authenticateObject = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(authenticateObject);
        return ResponseEntity.ok().body(jwtService.createToken((User) authentication.getPrincipal()));
    }

    @PostMapping(value = "/sing-up")
    public ResponseEntity<TokenDto> singUp(@RequestBody SingUpRequestDto registerDto){
        var auth = userservice.loadUserByUsername(registerDto.email());
        if(  auth != null){
            throw new RuntimeException("email ou senha em uso");
        }
        var response = userservice.singUp(registerDto);
        var token = jwtService.createToken(userservice.findById(response.id()).get());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(token);

    }
}
