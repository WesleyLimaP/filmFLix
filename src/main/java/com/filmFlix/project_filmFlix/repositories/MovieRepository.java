package com.filmFlix.project_filmFlix.repositories;

import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = "SELECT TB_MOVIE.TITLE, TB_MOVIE.SUB_TITLE, TB_MOVIE.MOVIE_YEAR , TB_MOVIE.IMG_URL " +
            "FROM TB_MOVIE " +
            "where TB_MOVIE.GENRE_ID  = :idGenre"
    )
    Page<MovieProjection> findAll(int idGenre, Pageable pageable);
    Page<Movie> findAll(Pageable pageable);
}
