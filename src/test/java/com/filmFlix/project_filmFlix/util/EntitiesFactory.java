package com.filmFlix.project_filmFlix.util;

import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieDetailsDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieInsertDto;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewDto;
import com.filmFlix.project_filmFlix.entities.Genre;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.entities.Review;
import com.filmFlix.project_filmFlix.entities.User;

public class EntitiesFactory {

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

}
