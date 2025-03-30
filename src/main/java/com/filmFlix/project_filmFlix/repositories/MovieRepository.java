package com.filmFlix.project_filmFlix.repositories;

import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieInfoProjection;
import com.filmFlix.project_filmFlix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = "SELECT TB_MOVIE.TITLE, TB_MOVIE.SUB_TITLE, TB_MOVIE.MOVIE_YEAR , TB_MOVIE.IMG_URL " +
            "FROM TB_MOVIE " +
            "where TB_MOVIE.GENRE_ID  = :idGenre"
    )
    Page<MovieProjection> findByGenre(int idGenre, Pageable pageable);

    Page<Movie> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = """
            SELECT TB_MOVIE.TITLE, TB_MOVIE.SUB_TITLE, TB_MOVIE.MOVIE_YEAR, TB_MOVIE.IMG_URL, TB_MOVIE.SYNOPSIS, TB_REVIEW.TEXT, TB_USER.NAME
            FROM TB_MOVIE
            INNER JOIN TB_REVIEW ON TB_MOVIE.ID = TB_REVIEW.MOVIE_ID
            INNER JOIN TB_USER ON TB_USER.ID = TB_REVIEW.USER_ID
            WHERE TB_MOVIE.ID = :id""")
    List<MovieInfoProjection>searchById(Long id);
}
