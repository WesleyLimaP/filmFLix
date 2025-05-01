package com.filmFlix.project_filmFlix.entities;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.*;

@Entity
@Table(name = "tb_recover_Password")
public class PasswordRecover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String token;
    @Value("$email.password-recover.token.minutes}")
    private final LocalDateTime expiration = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusMinutes(30);

    public PasswordRecover(String email, String token) {
        this.email = email;
        this.token = token;
    }
   public PasswordRecover(String email, String token, Long expiration) {
        this.email = email;
        this.token = token;
    }

    public PasswordRecover() {

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public Long getId() {
        return id;
    }
}
