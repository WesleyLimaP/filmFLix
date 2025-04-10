package com.filmFlix.project_filmFlix.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.filmFlix.project_filmFlix.dtos.authDtos.TokenDto;
import com.filmFlix.project_filmFlix.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    @Value("${api.security.token.secret}")
    private String rsaPublicKey;


    public TokenDto createToken(User user) throws JWTCreationException{
            Algorithm algorithm = Algorithm.HMAC256(rsaPublicKey);
            var token = JWT.create()
                    .withIssuer("filmFlix")
                    .withClaim("id", user.getId())
                    .withSubject(user.getEmail())
                    .withClaim("role:", String.valueOf(user.getRole().getAuthority()))
                    .withExpiresAt(Instant.now().plusSeconds(1800))
                    .sign(algorithm);
            return new TokenDto(token);
    }

    public String getSubject (String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(rsaPublicKey);
            return JWT.require(algorithm)
                    .withIssuer("filmFlix")
                    .build()
                    .verify(token).getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("token invalido");
        }

    }
}
