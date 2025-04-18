package com.filmFlix.project_filmFlix.util;

import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieDetailsDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieInsertDto;
import com.filmFlix.project_filmFlix.entities.*;
import com.filmFlix.project_filmFlix.dtos.authDtos.SingUpRequestDto;
import com.filmFlix.project_filmFlix.enums.Authority;
import org.springframework.security.core.userdetails.UserDetails;

public class EntitiesFactory {
    public static SingUpRequestDto createSingUpRequest(){
        return new SingUpRequestDto("wesley", "teste", "wesleylima029@gmail.com");
    }
    public static MovieInsertDto createMovieInsertDto(){
        return new MovieInsertDto(

                "O Senhor dos Anéis",
                "A Sociedade do Anel",
                2001L,
                "https://imagem.com/poster.jpg",
                "Um grupo parte em uma jornada para destruir o Um Anel.",
                1L
        );
    }
    public static Movie createMovie(){
       var movie=  new Movie(

                "O Senhor dos Anéis",
                "A Sociedade do Anel",
                2001L,
                "https://imagem.com/poster.jpg",
                "Um grupo parte em uma jornada para destruir o Um Anel.",
                new Genre(1L)
        );
        movie.getReviews().add(new Review("demais!", new User(1L), new Movie(1L)));
        return movie;
    }
    public static MovieDetailsDto createMovieDetails(){
        var dto = new MovieDetailsDto(

                "O Senhor dos Anéis",
                "A Sociedade do Anel",
                2001L,
                "https://imagem.com/poster.jpg",
                "Um grupo parte em uma jornada para destruir o Um Anel.",
                "Comédia"
        );
        return dto;
    }

    public static UserDetails createUser(){
        return User.builder()
                .id(1L)
                .name("Wesley")
                .email("wesleylima029@gmail.com")
                .password("teste")
                .role(new Role(Authority.ROLE_MEMBER))
                .build();
    }

}
