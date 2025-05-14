package com.filmFlix.project_filmFlix.controllers;
import com.filmFlix.project_filmFlix.dtos.authDtos.LoginRequest;
import com.filmFlix.project_filmFlix.dtos.authDtos.SingUpRequestDto;
import com.filmFlix.project_filmFlix.dtos.authDtos.TokenDto;
import com.filmFlix.project_filmFlix.dtos.authDtos.PasswordRecoverDtos.EmailMinDto;
import com.filmFlix.project_filmFlix.dtos.authDtos.PasswordRecoverDtos.newPasswordDto;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.services.JwtService;
import com.filmFlix.project_filmFlix.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        var authenticateObject = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(authenticateObject);
        return ResponseEntity.ok().body(jwtService.createToken((User) authentication.getPrincipal()));
    }

    @PostMapping(value = "/sing-up")
    public ResponseEntity<TokenDto> singUp(@Valid @RequestBody SingUpRequestDto registerDto){
        var response = authService.singUp(registerDto);
        var token = jwtService.createToken(response);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(token);

    }
    @PostMapping(value = "/recover-password")
    public ResponseEntity<Void> recoverPassword(@Valid @RequestBody EmailMinDto email){
        authService.recoverPassword(email.email());
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value = "/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody newPasswordDto request){
        authService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }
}
