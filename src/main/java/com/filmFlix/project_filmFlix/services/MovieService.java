package com.filmFlix.project_filmFlix.services;

import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.*;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieMinProjection;
import com.filmFlix.project_filmFlix.repositories.GenreRepository;
import com.filmFlix.project_filmFlix.repositories.MovieRepository;
import com.filmFlix.project_filmFlix.services.util.MovieServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TmdbApiService tmdbApi;

    @Transactional
    public Page<MovieDto> findAll(String idGenre, String movieName, Pageable pageable) {
        List<Long> genres = new ArrayList<>();
        if(!idGenre.equals("0")){
            genres = Arrays.stream(idGenre.split(",")).map(Long::parseLong).toList();

        }
        var movieIds = repository.searchMovies(genres, movieName, pageable);
        var movies = repository.searchMoviesWithGenres(movieIds.map(MovieMinProjection::getId).stream().toList());
        var orderedList = MovieServiceUtils.orderList(movies, movieIds.getContent());
        var dto = orderedList.stream().map(MovieDto::new).toList();
        return new PageImpl<>(dto, movieIds.getPageable(), movieIds.getTotalElements());

    }

    @Transactional
    public MovieDetailsDto findById(Long id){
        var result = repository.searchById(id);
        return new MovieDetailsDto(result.orElseThrow(() ->
                new ResourcesNotFoundException("filme nao encontrado")));
    }

    @Transactional
    public MovieResponseDto insert(MovieInsertRequestDto dto) throws IOException, InterruptedException {
        Movie movie = new Movie();
        var movieTmdb = tmdbApi.getMovie(dto.getTitle(), dto.getSubTitle());
        movie.setTrailer(tmdbApi.getTrailerYt(movieTmdb.path("id").asText()));
        movie.setTitle(dto.getTitle());
        movie.setSubTitle(dto.getSubTitle());
        movie.getGenres().add(genreRepository.findById(dto.getGenre().stream().iterator().next()).orElseThrow(()-> new ResourcesNotFoundException("genero nao encontrado")));
        movie.setSynopsis(movieTmdb.path("overview").asText());
        var imageUrl = "https://image.tmdb.org/t/p/w400/";
        movie.setImgUrl(imageUrl + movieTmdb.path("poster_path").asText());
        movie.setMovieYear(Long.parseLong(Arrays.stream(movieTmdb.path("release_date").asText().split("-")).findFirst().get()));
        var entitie = repository.save(movie);
        return new MovieResponseDto(entitie);
    }
    @Transactional
    public void delete(Long id) {
        var movie = repository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("filme nao encontrado"));
        movie.getGenres().clear();
        repository.removeById(movie.getId());
    }
    @Transactional
    public MovieResponseDto update(Long id, MovieUpdateRequestDto dto){
        var entitie = repository.findById(id).orElseThrow(() ->
                new ResourcesNotFoundException("filme nao encontrado"));

        var genreList = dto.getGenres().stream().map(x -> genreRepository.findById(x.getId())
                .orElseThrow(()-> new ResourcesNotFoundException("genero nao encontrado"))).toList();

        entitie.getGenres().clear();
        entitie.getGenres().addAll(genreList);
        entitie.setMovieYear(dto.getMovieYear());
        entitie.setSynopsis(dto.getSynopsis());
        entitie.setTitle(dto.getTitle());
        entitie.setSubTitle(dto.getSubTitle());
        entitie.setImgUrl(dto.getImgUrl());

        return new MovieResponseDto(repository.save(entitie));
    }

}
