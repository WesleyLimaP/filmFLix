package com.filmFlix.project_filmFlix.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.filmFlix.project_filmFlix.dtos.TokenDto;
import com.filmFlix.project_filmFlix.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class JwtService {
    @Value("${api.security.token.secret}")
    private String rsaPublicKey;

    @Transactional
    public TokenDto createToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(rsaPublicKey);
            var token = JWT.create()
                    .withIssuer("filmFlix")
                    .withClaim("id", user.getId())
                    .withSubject(user.getEmail())
                    .withClaim("role:", String.valueOf(user.getRole().getAuthority()))
                    .withExpiresAt(Instant.now().plusSeconds(1800))
                    .sign(algorithm);
            return new TokenDto(token);
        } catch (JWTCreationException exception) {
            System.out.println("erro ao gerar o token");
        }

        return null;
    }
    @Transactional
    public String getSubject (String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(rsaPublicKey);
            return JWT.require(algorithm)
                    .withIssuer("filmFlix")
                    .build()
                    .verify(token).getSubject();
        } catch (JWTVerificationException exception){
            System.out.println("token invalido");
        }
        return null;
    }
}
