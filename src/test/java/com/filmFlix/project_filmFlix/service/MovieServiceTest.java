package com.filmFlix.project_filmFlix.service;

import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieDetailsDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieInsertDto;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieDetailsProjection;
import com.filmFlix.project_filmFlix.projections.MovieProjection;
import com.filmFlix.project_filmFlix.repositories.MovieRepository;
import com.filmFlix.project_filmFlix.services.MovieService;
import com.filmFlix.project_filmFlix.util.EntitiesFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository repository;
    @InjectMocks
    private MovieService service;
    private Long validId;
    private Long invalidId;
    private MovieDetailsProjection projection;
    private MovieProjection movieProjection;
    private PageImpl<MovieProjection> page;
    private PageImpl<MovieProjection> pageNull;
    private MovieInsertDto insertDto;
    private Movie movie;
    @BeforeEach
    void setUp(){
        insertDto = EntitiesFactory.createMovieInsertDto();
        movie = EntitiesFactory.createMovie();

        projection = Mockito.mock(MovieDetailsProjection.class);
        movieProjection = Mockito.mock(MovieProjection.class);

        page = new PageImpl<>(List.of(movieProjection));
        pageNull = new PageImpl<>(List.of());


        validId = 1L;
        invalidId = 2L;

        //searchById
        Mockito.when(repository.searchById(validId)).thenReturn(List.of(projection));
        Mockito.doThrow(ResourcesNotFoundException.class).when(repository).searchById(invalidId);
        Mockito.when(projection.getTitle()).thenReturn("O Senhor dos Anéis");

        //searchAll/searchByGenre
        Mockito.when(repository.searchAll(ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(repository.findByGenre(eq(validId), ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(movieProjection.getGenre_Id()).thenReturn(validId);
        Mockito.when(repository.findByGenre(eq(invalidId), ArgumentMatchers.any())).thenReturn(pageNull);

        //save
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(movie);

        //delete
        Mockito.when(repository.removeById(validId)).thenReturn(3);
        Mockito.doThrow(ResourcesNotFoundException.class).when(repository).removeById(invalidId);

        //update
        Mockito.when(repository.findById(validId)).thenReturn(Optional.of(movie));

    }

    @Test
    public void findByIdShouldReturnMovieDetailDtoWhenIdExist(){
        var movie = service.findById(validId);
        Assertions.assertNotNull(movie);
        Assertions.assertEquals(MovieDetailsDto.class, movie.getClass());
        Assertions.assertEquals("O Senhor dos Anéis", movie.getTitle());


    }
    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenInvalidId(){
        Assertions.assertThrows(
                ResourcesNotFoundException.class, () -> {
                    service.findById(invalidId);
                }
        );
    }
    @Test
    public void findAllShouldReturnAPageWithAllMoviesWhenGenreIdIsNull(){
        var pageable = PageRequest.of(0, 5);
       var pages = service.findAll(0L, pageable);
       Assertions.assertNotNull(pages);
       Assertions.assertTrue(pages.getTotalElements() > 0);

    }
    @Test
    public void findAllShouldReturnOnlyMoviesPageMatchingGivenGenreId(){
    var pageable = PageRequest.of(0, 5);
       var pages = service.findAll(validId, pageable);
       Mockito.verify(repository, Mockito.times(1)).findByGenre(eq(validId), ArgumentMatchers.any());
       Assertions.assertEquals(1L,pages.get().findFirst().get().getGenreId());
       Assertions.assertNotNull(pages);


    }
    @Test
    public void findAllShouldReturnEmptyPageWhenGenreIdIsNotExist(){
    var pageable = PageRequest.of(0, 5);
       var pages = service.findAll(invalidId, pageable);
       Assertions.assertEquals(0,  pages.getTotalElements());

    }
    @Test
    public void insertShouldSave(){
        var dto = service.insert(insertDto);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals("O Senhor dos Anéis", dto.getTitle());
        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any());

    }
    @Test
    public void deleteShouldDoNothingWhenValidId(){
        service.delete(validId);
        Assertions.assertDoesNotThrow(() ->service.delete(validId));
        Assertions.assertTrue(repository.removeById(validId) > 0);

    }
    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenInvalidId(){
       Assertions.assertThrows(ResourcesNotFoundException.class, () -> {
         service.delete(invalidId);
       });

    }
    @Test
    public void updateShouldReturnMovieDetailsDtoWhenValidId(){
        var dto = service.update(validId, insertDto);
        Assertions.assertEquals(MovieDetailsDto.class, dto.getClass());
        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any());

    }
    @Test
    public void updateShouldThrowResourceNOtFoundExceptionWhenInvalidId(){
        Assertions.assertThrows(ResourcesNotFoundException.class, () -> {
            service.update(invalidId, insertDto);
        });

    }




}
