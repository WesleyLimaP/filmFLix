package com.filmFlix.project_filmFlix.services;

import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieDetailsDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieInsertDto;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewDto;
import com.filmFlix.project_filmFlix.entities.Genre;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieDetailsProjection;
import com.filmFlix.project_filmFlix.projections.MovieProjection;
import com.filmFlix.project_filmFlix.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;


@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

    @Transactional
    public Page<MovieDto> findAll(Long idGenre, Pageable pageable){
        if(idGenre != 0){
            Page<MovieProjection> projection = repository.findByGenre(idGenre, pageable);
            return projection.map(MovieDto::new);
        }
        Page<MovieProjection> projection = repository.searchAll(pageable);
        return projection.map(MovieDto::new);
    }

    @Transactional
    public MovieDetailsDto findById(Long id){
       var result = repository.searchById(id);
       MovieDetailsDto movieInfoDto = new MovieDetailsDto(result.getFirst());

        for (MovieDetailsProjection reviews : result) {
                movieInfoDto.getReviews().add(new ReviewDto(reviews));
        }
        return movieInfoDto;
    }
    @Transactional
    public MovieInsertDto insert(MovieInsertDto dto){
        Movie movie = new Movie(dto.getTitle(), dto.getSubTitle(), dto.getYear(), dto.getImgUrl(), dto.getSynopsis(), new Genre(dto.getGenre()));
        var entitie = repository.save(movie);
        return Stream.of(entitie).map(x -> new MovieInsertDto(entitie)).toList().getFirst();
    }
    @Transactional
    public void delete(Long id){
        repository.removeById(id);
    }
    @Transactional
    public MovieDetailsDto update(Long id, MovieInsertDto dto){
        var entitie = repository.findById(id).orElseThrow(() -> new RuntimeException("id nao encontrado"));

        entitie.setGenre(new Genre(dto.getGenre()));
        entitie.setMovieYear(dto.getYear());
        entitie.setSynopsis(dto.getSynopsis());
        entitie.setTitle(dto.getTitle());
        entitie.setSubTitle(dto.getSubTitle());
        entitie.setImgUrl(dto.getImgUrl());

        repository.save(entitie);

        return Stream.of(entitie).map(MovieDetailsDto::new).toList().getFirst();
    }
}
