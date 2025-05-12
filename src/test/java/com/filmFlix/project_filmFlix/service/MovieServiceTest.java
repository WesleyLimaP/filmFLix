package com.filmFlix.project_filmFlix.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieDetailsDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieInsertRequestDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieResponseDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieUpdateRequestDto;
import com.filmFlix.project_filmFlix.entities.Genre;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieDetailsProjection;
import com.filmFlix.project_filmFlix.projections.MovieMinProjection;
import com.filmFlix.project_filmFlix.projections.MovieProjection;
import com.filmFlix.project_filmFlix.repositories.GenreRepository;
import com.filmFlix.project_filmFlix.repositories.MovieRepository;
import com.filmFlix.project_filmFlix.services.MovieService;
import com.filmFlix.project_filmFlix.services.TmdbApiService;
import com.filmFlix.project_filmFlix.services.util.MovieServiceUtils;
import com.filmFlix.project_filmFlix.util.EntitiesFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository repository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private TmdbApiService tmdbApiService;
    @InjectMocks
    private MovieService service;

    private Long validId;
    private Long invalidId;
    private MovieDetailsProjection projection;
    private MovieMinProjection movieMinProjection;
    private MovieProjection movieProjection;
    private PageImpl<MovieMinProjection> page;
    private PageImpl<MovieProjection> pageNull;
    private MovieInsertRequestDto insertDto;
    private Movie movie;
    private Pageable pageable;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        insertDto = EntitiesFactory.createMovieInsertDto();
        movie = EntitiesFactory.createMovie();
        pageable = PageRequest.of(0, 5);

        projection = Mockito.mock(MovieDetailsProjection.class);
        movieProjection = Mockito.mock(MovieProjection.class);
        movieMinProjection = Mockito.mock(MovieMinProjection.class);

        Mockito.when(movieMinProjection.getId()).thenReturn(1L);
        Mockito.when(movieMinProjection.getTitle()).thenReturn("Bob Esponja");

        page = new PageImpl<>(List.of(movieMinProjection));
        pageNull = new PageImpl<>(List.of());

        validId = 1L;
        invalidId = 2L;

        Mockito.when(tmdbApiService.getMovie(any(), any())).thenReturn(new ObjectMapper().readTree("""
            {
                "overview": "Um ladrão que invade sonhos.",
                "poster_path": "/test.jpg",
                "release_date": "2010-07-16",
                "id": "123"
            }
        """));

        // searchById
        Mockito.when(repository.searchById(validId)).thenReturn(Optional.of(movie));
        Mockito.when(genreRepository.findById(validId)).thenReturn(Optional.of(new Genre(1L)));
        Mockito.doThrow(ResourcesNotFoundException.class).when(repository).searchById(invalidId);
        Mockito.when(projection.getTitle()).thenReturn("O Senhor dos Anéis");

        // searchAll/searchByGenre
        Mockito.when(repository.searchMovies(any(), any(), any())).thenReturn(page);
        Mockito.when(repository.searchMoviesWithGenres(any())).thenReturn(List.of(movie));

        Mockito.when(movieProjection.getGenre_Id()).thenReturn(validId);
        Mockito.doThrow(ResourcesNotFoundException.class).when(repository).searchMoviesWithGenres(List.of(invalidId));
        Mockito.doThrow(ResourcesNotFoundException.class).when(repository).searchMovies(eq(List.of(invalidId)), any(), any());

        // save
        Mockito.when(repository.save(any())).thenReturn(movie);

        // delete
        Mockito.when(repository.removeById(validId)).thenReturn(3);
        Mockito.doThrow(ResourcesNotFoundException.class).when(repository).removeById(invalidId);

        // update
        Mockito.when(repository.findById(validId)).thenReturn(Optional.of(movie));
    }

    // Testes para o metodo findById
    @Test
    public void findByIdShouldReturnMovieDetailDtoWhenIdExist() {
        var movie = service.findById(validId);
        Assertions.assertNotNull(movie);
        Assertions.assertEquals(MovieDetailsDto.class, movie.getClass());
        Assertions.assertEquals("O Labirinto do Fauno", movie.getTitle());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourcesNotFoundException.class, () -> {
            service.findById(invalidId);
        });
    }

    // Testes para o metodo findAll
    @Test
    public void findAllShouldReturnAPageWithAllMoviesWhenGenreIdIsNull() {
        try (MockedStatic<MovieServiceUtils> mocked = Mockito.mockStatic(MovieServiceUtils.class)) {
            mocked.when(() -> MovieServiceUtils.orderList(anyList(), anyList())).thenReturn(List.of(movie));
            var pages = service.findAll("0", "", pageable);
            Assertions.assertNotNull(pages);
            Assertions.assertTrue(pages.getTotalElements() > 0);
            Mockito.verify(repository, Mockito.times(1)).searchMovies(any(), any(), any());
            Mockito.verify(repository, Mockito.times(1)).searchMoviesWithGenres(any());
        }
    }

    @Test
    public void findAllShouldReturnOnlyMoviesPageMatchingGivenGenreId() {
        try (MockedStatic<MovieServiceUtils> mocked = Mockito.mockStatic(MovieServiceUtils.class)) {
            mocked.when(() -> MovieServiceUtils.orderList(anyList(), anyList())).thenReturn(List.of(movie));
            var pages = service.findAll("1", "", pageable);
            Mockito.verify(repository, Mockito.times(1)).searchMoviesWithGenres(any());
            Assertions.assertEquals("O Labirinto do Fauno", pages.get().findFirst().get().getTitle());
            Assertions.assertNotNull(pages);
        }
    }

    @Test
    public void findAllShouldReturnEmptyPageWhenGenreIdIsNotExist() {
        Assertions.assertThrows(ResourcesNotFoundException.class, () -> {
            service.findAll(String.valueOf(invalidId), "", pageable);
        });
    }

    // Testes para o metodo insert
    @Test
    public void insertShouldSave() throws IOException, InterruptedException {
        var dto = service.insert(insertDto);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals("O Labirinto do Fauno", dto.getTitle());
        Mockito.verify(repository, Mockito.times(1)).save(any());
    }

    // Testes para o metodo delete
    @Test
    public void deleteShouldDoNothingWhenValidId() {
        service.delete(validId);
        Mockito.verify(repository, Mockito.times(1)).removeById(any());
        Assertions.assertDoesNotThrow(() -> service.delete(validId));
        Assertions.assertTrue(repository.removeById(validId) > 0);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourcesNotFoundException.class, () -> {
            service.delete(invalidId);
        });
    }

    // Testes para o metodo insert
    @Test
    public void updateShouldReturnMovieDetailsDtoWhenValidId() {
        var dto = service.update(validId, new MovieUpdateRequestDto("O Senhor dos Anéis", "teste", 2000L, "teste", "teste", "teste"));
        Assertions.assertEquals(MovieResponseDto.class, dto.getClass());
        Assertions.assertEquals(insertDto.getTitle(), dto.getTitle());
        Mockito.verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourcesNotFoundException.class, () -> {
            service.update(invalidId, new MovieUpdateRequestDto("O Senhor dos Anéis", "teste", 2000L, "teste", "teste", "teste"));
        });
        Mockito.verify(repository, Mockito.times(1)).findById(invalidId);
    }
    }


