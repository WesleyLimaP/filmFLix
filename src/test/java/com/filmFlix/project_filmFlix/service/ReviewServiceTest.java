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
        user = new User(1L);
        review = new Review();
        review.setId(validId);
        review.setText("Muito bom!");
        review.setMovie(movie);
        review.setUser(user);
        requestDto = new ReviewRequestDto("Muito bom!", 10.0 );

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

    // Testa a funcionalidade de encontrar todos os reviews por filme
    @Test
    void findAllByMovieShouldReturnPageOfReviewMaxDto() {
        Page<ReviewMaxDto> result = reviewService.findAllByMovie(pageable, validId);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(reviewRepository, times(1)).findAllByMovie(pageable, validId);
    }
    // Testa a funcionalidade de encontrar um review pelo ID
    @Test
    void findByIdShouldReturnReviewMaxDtoWhenValidId() {
        ReviewMaxDto result = reviewService.findById(validId);
        assertNotNull(result);
        assertEquals(ReviewMaxDto.class, result.getClass());
        verify(reviewRepository, times(1)).findById(validId);
    }

    // Testa a exceção quando o ID do review não é encontrado
    @Test
    void findByIdShouldThrowResourcesNotFoundExceptionWhenInvalidId() {
        assertThrows(ResourcesNotFoundException.class, () -> {
            reviewService.findById(invalidId);
        });
        verify(reviewRepository, times(1)).findById(invalidId);
    }

    // Testa a inserção de um review com credenciais válidas
    @Test
    void insertShouldReturnReviewMaxDtoWhenValidCredentials() {
        mockAuthentication();
        ReviewMaxDto result = reviewService.insert(validId, new ReviewRequestDto());
        assertNotNull(result);
        assertEquals(ReviewMaxDto.class, result.getClass());
        verify(reviewRepository, times(1)).save(any());
        verify(movieRepository, times(1)).findById(validId);
    }

    // Testa a exceção quando o filme não é encontrado ao tentar inserir um review
    @Test
    void insertShouldThrowResourcesNotFoundExceptionWhenMovieNotFound() {
        mockAuthentication();
        Mockito.when(movieRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourcesNotFoundException.class, () -> {
            reviewService.insert(invalidId, requestDto);
        });

        verify(movieRepository, times(1)).findById(invalidId);
        verify(reviewRepository, times(0)).save(any());
    }

    // Testa a atualização de um review com credenciais válidas
    @Test
    void updateShouldReturnReviewMaxDtoWhenValidCredentials() {
        mockAuthentication(); // Mock da autenticação de usuário

        ReviewMaxDto result = reviewService.update(validId, requestDto);
        assertNotNull(result);
        assertEquals(ReviewMaxDto.class, result.getClass());
        verify(reviewRepository, times(1)).findById(validId);
        verify(reviewRepository, times(1)).save(any());
    }

    // Testa a exceção quando o usuário tenta atualizar um review que não lhe pertence
    @Test
    void updateShouldThrowUnauthorizedExceptionWhenUserIsNotOwner() {
        mockAuthenticationWithDifferentUser();
        assertThrows(UnauthorizedException.class, () -> {
            reviewService.update(validId, requestDto);
        });

        verify(reviewRepository, times(1)).findById(validId);
        verify(reviewRepository, times(0)).save(any());
    }

    // Testa a exclusão de um review quando o usuário é o proprietário
    @Test
    void deleteShouldDoNothingWhenUserIsOwner() {
        mockAuthentication();

        reviewService.delete(validId);

        verify(reviewRepository, times(1)).findById(validId);
        verify(reviewRepository, times(1)).deleteById(validId);
    }

    // Testa a exceção quando o usuário tenta excluir um review que não lhe pertence
    @Test
    void deleteShouldThrowUnauthorizedExceptionWhenUserIsNotOwner() {
        mockAuthenticationWithDifferentUser();

        assertThrows(UnauthorizedException.class, () -> {
            reviewService.delete(validId);
        });

        verify(reviewRepository, times(1)).findById(validId);
        verify(reviewRepository, times(0)).deleteById(any());
    }

    // Metodo para mockar a autenticação com o usuário correto
    private void mockAuthentication() {
        var authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        var context = Mockito.mock(SecurityContext.class);
        Mockito.when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
    }

    // Metodo para mockar a autenticação com um usuário diferente
    private void mockAuthenticationWithDifferentUser() {
        User otherUser = new User(99L);
        var authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(otherUser);
        var context = Mockito.mock(SecurityContext.class);
        Mockito.when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
    }

}
