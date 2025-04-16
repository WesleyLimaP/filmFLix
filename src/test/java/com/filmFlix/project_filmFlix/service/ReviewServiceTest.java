package com.filmFlix.project_filmFlix.service;

import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.Exceptions.UnauthorizedException;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewMaxDto;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewRequestDto;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.entities.Review;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.projections.ReviewProjection;
import com.filmFlix.project_filmFlix.repositories.MovieRepository;
import com.filmFlix.project_filmFlix.repositories.ReviewRepository;
import com.filmFlix.project_filmFlix.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Pageable pageable;
    private Long validId;
    private Long invalidId;
    private Review review;
    private ReviewRequestDto requestDto;
    private Movie movie;
    private User user;
    private ReviewProjection reviewProjection;
    private PageImpl<ReviewProjection> page;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 5);
        validId = 1L;
        invalidId = 2L;
        movie = new Movie();
        user = new User();
        user.setId(1L);
        review = new Review();
        review.setId(validId);
        review.setText("Muito bom!");
        review.setMovie(movie);
        review.setUser(user);
        requestDto = new ReviewRequestDto(validId,"Muito bom!" );

        reviewProjection = Mockito.mock(ReviewProjection.class);
        page = new PageImpl<>(List.of(reviewProjection));

        // findAllByMovie
        Mockito.when(reviewRepository.findAllByMovie(pageable, validId)).thenReturn(page);

        // findById
        Mockito.when(reviewRepository.findById(validId)).thenReturn(Optional.of(review));
        Mockito.when(reviewRepository.findById(invalidId)).thenReturn(Optional.empty());

        // insert
        Mockito.when(movieRepository.findById(validId)).thenReturn(Optional.of(movie));
        Mockito.when(reviewRepository.save(any())).thenReturn(review);

        // update
        Mockito.when(reviewRepository.save(review)).thenReturn(review);
    }

    @Test
    void findAllByMovieShouldReturnPageOfReviewMaxDto() {
        Page<ReviewMaxDto> result = reviewService.findAllByMovie(pageable, validId);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(reviewRepository, times(1)).findAllByMovie(pageable, validId);
    }

    @Test
    void findByIdShouldReturnReviewMaxDtoWhenValidId() {
        ReviewMaxDto result = reviewService.findById(validId);
        assertNotNull(result);
        assertEquals(ReviewMaxDto.class, result.getClass());
        verify(reviewRepository, times(1)).findById(validId);
    }

    @Test
    void findByIdShouldThrowResourcesNotFoundExceptionWhenInvalidId() {
        assertThrows(ResourcesNotFoundException.class, () -> {
            reviewService.findById(invalidId);
        });
        verify(reviewRepository, times(1)).findById(invalidId);
    }

    @Test
    void insertShouldReturnReviewMaxDtoWhenValidCredentials() {
        mockAuthentication();

        ReviewMaxDto result = reviewService.insert(requestDto);
        assertNotNull(result);
        assertEquals(ReviewMaxDto.class, result.getClass());
        verify(reviewRepository, times(1)).save(any());
        verify(movieRepository, times(1)).findById(validId);
    }

    @Test
    void insertShouldThrowResourcesNotFoundExceptionWhenMovieNotFound() {
        mockAuthentication();
        Mockito.when(movieRepository.findById(invalidId)).thenReturn(Optional.empty());
        ReviewRequestDto invalidDto = new ReviewRequestDto( invalidId,"Texto");

        assertThrows(ResourcesNotFoundException.class, () -> {
            reviewService.insert(invalidDto);
        });

        verify(movieRepository, times(1)).findById(invalidId);
        verify(reviewRepository, times(0)).save(any());
    }

    @Test
    void updateShouldReturnReviewMaxDtoWhenValidCredentials() {
        mockAuthentication();

        ReviewMaxDto result = reviewService.update(validId, requestDto);
        assertNotNull(result);
        assertEquals(ReviewMaxDto.class, result.getClass());
        verify(reviewRepository, times(1)).findById(validId);
        verify(reviewRepository, times(1)).save(any());
    }

    @Test
    void updateShouldThrowUnauthorizedExceptionWhenUserIsNotOwner() {
        mockAuthenticationWithDifferentUser();

        assertThrows(UnauthorizedException.class, () -> {
            reviewService.update(validId, requestDto);
        });

        verify(reviewRepository, times(1)).findById(validId);
        verify(reviewRepository, times(0)).save(any());
    }

    @Test
    void deleteShouldDoNothingWhenUserIsOwner() {
        mockAuthentication();

        reviewService.delete(validId);

        verify(reviewRepository, times(1)).findById(validId);
        verify(reviewRepository, times(1)).deleteById(validId);
    }

    @Test
    void deleteShouldThrowUnauthorizedExceptionWhenUserIsNotOwner() {
        mockAuthenticationWithDifferentUser();

        assertThrows(UnauthorizedException.class, () -> {
            reviewService.delete(validId);
        });

        verify(reviewRepository, times(1)).findById(validId);
        verify(reviewRepository, times(0)).deleteById(any());
    }

    private void mockAuthentication() {
        var authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        var context = Mockito.mock(SecurityContext.class);
        Mockito.when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
    }

    private void mockAuthenticationWithDifferentUser() {
        User otherUser = new User();
        otherUser.setId(99L);
        var authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(otherUser);
        var context = Mockito.mock(SecurityContext.class);
        Mockito.when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
    }

}
