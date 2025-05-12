package com.filmFlix.project_filmFlix.repositories;

import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieMinProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(nativeQuery = true, value = """
            select *\s
            from(select distinct TB_MOVIE.ID, TB_MOVIE.TITLE
            from TB_MOVIE\s
            inner join TB_MOVIE_GENRE on TB_MOVIE_GENRE.MOVIE_ID = TB_MOVIE.ID\s
            where (:genreIds is null or TB_MOVIE_GENRE.GENRE_ID in :genreIds)
            and lower (TB_MOVIE.TITLE) like lower (concat('%',:name,'%')))\s
            as tb_result
           \s""",
            countQuery = """
                    select count(*)\s
                    from (select distinct TB_MOVIE.ID, TB_MOVIE.TITLE
                    from TB_MOVIE
                    inner join TB_MOVIE_GENRE  on TB_MOVIE_GENRE.MOVIE_ID = TB_MOVIE.ID
                    where (:genreIds is null or TB_MOVIE_GENRE.GENRE_ID in :genreIds)
                    and lower (TB_MOVIE.TITLE) like lower (concat('%',:name,'%'))) as tb_result""")
    Page<MovieMinProjection> searchMovies(List<Long>genreIds, String name, Pageable pageable);

    @Query("select obj from Movie obj join fetch obj.genres where obj.id in :movieIds ")
    List<Movie> searchMoviesWithGenres(List<Long>movieIds);

    @Query("select obj from Movie obj join fetch obj.reviews join fetch obj.genres where obj.id = :id")
    Optional<Movie> searchById(Long id);

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
