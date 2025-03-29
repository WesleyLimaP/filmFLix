package com.filmFlix.project_filmFlix.services;

import com.filmFlix.project_filmFlix.dtos.MovieDto;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieProjection;
import com.filmFlix.project_filmFlix.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

    @Transactional
    public Page<MovieDto> searchAll(Integer idGenre, Pageable pageable){
        if(!idGenre.equals(0)){
            Page<MovieProjection> projection = repository.findAll(idGenre, pageable);
            return projection.map(MovieDto::new);
        }
        Page<Movie> projection = repository.findAll(pageable);
        return projection.map(MovieDto::new);
    }
}
