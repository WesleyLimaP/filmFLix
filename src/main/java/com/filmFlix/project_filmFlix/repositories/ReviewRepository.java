package com.filmFlix.project_filmFlix.repositories;

import com.filmFlix.project_filmFlix.entities.Genre;
import com.filmFlix.project_filmFlix.entities.Review;
import com.filmFlix.project_filmFlix.projections.ReviewProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(nativeQuery = true, value =
    """
    SELECT TB_REVIEW.ID, TB_MOVIE.TITLE AS MOVIE_TITLE , TB_REVIEW.TEXT,TB_USER.NAME AS USER_NAME, TB_REVIEW.RATING
    FROM TB_REVIEW
    INNER JOIN TB_USER ON TB_USER.ID = TB_REVIEW.USER_ID
    INNER JOIN TB_MOVIE ON TB_MOVIE.ID = TB_REVIEW.MOVIE_ID
    WHERE TB_REVIEW.MOVIE_ID = :id""")
    Page<ReviewProjection> findAllByMovie(Pageable pageable, Long id);
}
