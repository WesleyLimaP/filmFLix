package com.filmFlix.project_filmFlix.repositories;

import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieDetailsProjection;
import com.filmFlix.project_filmFlix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = """
            SELECT TB_MOVIE.TITLE, TB_MOVIE.SUB_TITLE, TB_MOVIE.MOVIE_YEAR , TB_MOVIE.IMG_URL, TB_MOVIE.GENRE_ID, TB_MOVIE.USER_RATINGS
            FROM TB_MOVIE
            where TB_MOVIE.GENRE_ID = :idGenre"""
    )
    Page<MovieProjection> findByGenre(Long idGenre, Pageable pageable);

    @Query(nativeQuery = true, value = """
            SELECT TB_MOVIE.TITLE, TB_MOVIE.SUB_TITLE, TB_MOVIE.MOVIE_YEAR , TB_MOVIE.IMG_URL, TB_MOVIE.USER_RATINGS
            FROM TB_MOVIE
            """
    )
    Page<MovieProjection>searchAll(Pageable pageable);

    @Query(nativeQuery = true, value = """
            SELECT TB_MOVIE.TITLE, TB_MOVIE.SUB_TITLE, TB_MOVIE.MOVIE_YEAR, TB_MOVIE.IMG_URL, TB_MOVIE.SYNOPSIS, TB_REVIEW.TEXT, TB_USER.NAME, TB_GENRE.NAME, TB_MOVIE.USER_RATINGS\s
            FROM TB_MOVIE
            INNER JOIN TB_REVIEW ON TB_MOVIE.ID = TB_REVIEW.MOVIE_ID
            INNER JOIN TB_USER ON TB_USER.ID = TB_REVIEW.USER_ID
            INNER JOIN TB_GENRE ON TB_GENRE.ID = TB_MOVIE.GENRE_ID
            WHERE TB_MOVIE.ID = :id""")
    List<MovieDetailsProjection>searchById(Long id);

    @Modifying
    @Query(
            nativeQuery = true, value = """
                DELETE FROM TB_REVIEW
                WHERE TB_REVIEW.MOVIE_ID = :id;
                DELETE FROM TB_MOVIE WHERE TB_MOVIE.ID = :id
         """
    )
    Integer removeById(Long id);

    @Query(nativeQuery = true, value = """ 
            SELECT avg(RATING )
            from TB_REVIEW
            where MOVIE_ID = :id""")
    Double avg(Long id);
}
