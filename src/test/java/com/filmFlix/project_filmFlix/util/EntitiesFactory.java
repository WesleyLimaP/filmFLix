package com.filmFlix.project_filmFlix.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.filmFlix.project_filmFlix.dtos.authDtos.SingUpRequestDto;
import com.filmFlix.project_filmFlix.dtos.authDtos.LoginRequest;
import com.filmFlix.project_filmFlix.dtos.authDtos.TokenDto;
import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieDetailsDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieInsertRequestDto;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewRequestDto;
import com.filmFlix.project_filmFlix.entities.*;
import com.filmFlix.project_filmFlix.enums.Authority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.List;

public class EntitiesFactory {
    public static SingUpRequestDto createSingUpRequest(){
        return new SingUpRequestDto("wesley", "teste", "wesleylima029@gmail.com");
    }
    public static MovieInsertRequestDto createMovieInsertDto(){
        return new MovieInsertRequestDto("O Senhor dos Anéis","A Sociedade do Anel", List.of(1L, 2L));
    }
    public static MovieInsertRequestDto createMovieInsertDtoWitchInvalidGenre(){
        return new MovieInsertRequestDto("O Senhor dos Anéis", "A Sociedade do Anel", List.of(555L));
    }
    public static Movie createMovie(){
        Movie movie = new Movie(
                "O Labirinto do Fauno",
                null, // subTitle
                2006L,
                "https://image.tmdb.org/t/p/w500_and_h282_face/oXMfT5OM6HAgQ9sGANB8cs1ifCG.jpg",
                "Em 1944, na Espanha, a jovem Ofélia e sua mãe doente chegam ao posto do novo marido de sua mãe, um sádico oficial do exército. Enquanto explora um labirinto antigo, ela encontra um fauno que diz que ela é uma princesa perdida e precisa cumprir três tarefas para retornar ao seu reino.",
                "https://www.youtube.com/watch?v=EqYiSlkvRuw"
        );
        movie.getReviews().add(new Review("demais!", new User(1L), new Movie(1L)));
        movie.getGenres().addAll(List.of(new Genre(1L), new Genre(2L)));
        return movie;
    }
    public static MovieDetailsDto createMovieDetails(){
        MovieDetailsDto movieDetailsDto = new MovieDetailsDto(
                "O Labirinto do Fauno",
                null, // subTitle
                2006L,
                "https://image.tmdb.org/t/p/w500_and_h282_face/oXMfT5OM6HAgQ9sGANB8cs1ifCG.jpg",
                "Em 1944, na Espanha, a jovem Ofélia e sua mãe doente chegam ao posto do novo marido de sua mãe, um sádico oficial do exército. Enquanto explora um labirinto antigo, ela encontra um fauno que diz que ela é uma princesa perdida e precisa cumprir três tarefas para retornar ao seu reino.",
                8.7 // userRating
        );

        return movieDetailsDto;
    }

    public static UserDetails createUser(){
        return User.builder()
                .id(1L)
                .name("Bob")
                .email("limap16@gmail.com")
                .password("teste")
                .role(new Role(Authority.ROLE_ADM))
                .build();
    }
    public static TokenDto createToken() throws JWTCreationException, IllegalArgumentException {
        Algorithm algorithm = Algorithm.HMAC256("123456");
        var token = JWT.create()
                .withIssuer("filmFlix")
                .withClaim("id", 1L)
                .withSubject("limap16wesley@gmail.com")
                .withClaim("role", "ROLE_MEMBER")
                .withExpiresAt(Instant.now().plusSeconds(1800))
                .sign(algorithm);
        return new TokenDto(token);


    }
    public static TokenDto createADMToken() throws JWTCreationException, IllegalArgumentException {
        Algorithm algorithm = Algorithm.HMAC256("123456");
        var token = JWT.create()
                .withIssuer("filmFlix")
                .withClaim("id", 3L)
                .withSubject("wesley@gmail.com")
                .withClaim("role", "ROLE_ADM")
                .withExpiresAt(Instant.now().plusSeconds(1800))
                .sign(algorithm);
        return new TokenDto(token);


    }    public static TokenDto createInvalidToken() throws JWTCreationException, IllegalArgumentException {
        Algorithm algorithm = Algorithm.HMAC256("12356");
        var token = JWT.create()
                .withIssuer("filmFlix")
                .withClaim("id", 3L)
                .withSubject("wesley@gmail.com")
                .withClaim("role", "ROLE_ADM")
                .withExpiresAt(Instant.now().plusSeconds(1800))
                .sign(algorithm);
        return new TokenDto(token);


    }
    public static GenreDto createGenreDto(){
        return new GenreDto("Pop");
    }
    public static LoginRequest createLoginRequest(){
        return new LoginRequest("limap16wesley@gmail.com", "123456");
    }
    public static LoginRequest createInvalidLoginRequest(){
        return new LoginRequest("bobb@gmail.com", "123456");
    }
    public static SingUpRequestDto createSignUpRequest(){
        return new SingUpRequestDto("chester", "1234567h", "chester@gmail.com");

    }public static SingUpRequestDto createInvalidSignUpRequest(){
        return new SingUpRequestDto("wesley", "123456", "limap16wesley@gmail.com");
    }

    public static ReviewRequestDto createReviewRequestDto() {
        return new ReviewRequestDto("teste", 7.0);
    }
}
