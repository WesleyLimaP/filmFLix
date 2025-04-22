package com.filmFlix.project_filmFlix.service;

import com.filmFlix.project_filmFlix.Exceptions.DuplacationEntityException;
import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.entities.Genre;
import com.filmFlix.project_filmFlix.repositories.GenreRepository;
import com.filmFlix.project_filmFlix.services.GenreService;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GenreServiceTest {

    @Mock
    private GenreRepository repository;

    @InjectMocks
    private GenreService service;

    Pageable pageable;
    private Genre genre;
    private GenreDto genreDto;
    private Long validId;
    private Long invalidId;
    private Genre dupliateGenre;
    private GenreDto newGenre;
    private Genre updatedGenre;

    @BeforeEach
    void setUp() {
        validId = 1L;
        invalidId = 2L;
        genre = new Genre( "Ação");
        newGenre = new GenreDto("Ação");
        updatedGenre = new Genre(newGenre.getName());
        genreDto = new GenreDto("Comédia");
        pageable = PageRequest.of(0, 5);
        PageImpl<Genre> page = new PageImpl<>(List.of(genre));
        dupliateGenre = new Genre("genero existente");

        //findAll
        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        //findById
        Mockito.when(repository.findById(validId)).thenReturn(Optional.of(genre));
        Mockito.doThrow(ResourcesNotFoundException.class).when(repository).findById(invalidId);

    }

    // Teste para o findAll
    @Test
    void findAllShouldReturnAPageOfGenreDto() {
        Page<GenreDto> result = service.findAll(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(genre.getName(), result.getContent().get(0).getName());
        assertEquals(GenreDto.class, result.getContent().get(0).getClass());

        verify(repository, times(1)).findAll(pageable);
    }

    // Teste para findById (sucesso)
    @Test
    void findByIdShouldReturnGenreDtoWhenValidId() {
        GenreDto result = service.findById(1L);
        assertNotNull(result);
        assertEquals(genre.getName(), result.getName());
        verify(repository, times(1)).findById(1L);
        assertEquals(GenreDto.class, result.getClass());
    }

    // Teste para findById (falha: id inexistente)
    @Test
    void findByIdShouldThrowResourcesNotFoundExceptionWhenInvalidId() {
        assertThrows(ResourcesNotFoundException.class, ()->{
            service.findById(invalidId);
        });

        verify(repository, times(1)).findById(invalidId);
    }

    // Teste para insert (sucesso)
    @Test
    void insertShouldReturnGenreDtoWhenValidCredentials() {
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(genre);

        GenreDto result = service.insert(newGenre);
        assertNotNull(result);
        assertEquals(newGenre.getName(), result.getName());
        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any());
        Assertions.assertEquals(GenreDto.class, result.getClass());
    }
    // Teste para insert (falha: dados existentes no banco de dados)
    @Test
    void insertShouldThrowDuplicationExceptionWhenInvalidCredentials() {
        Mockito.doThrow(DuplacationEntityException.class).when(repository).save(ArgumentMatchers.any());
        Assertions.assertThrows(DuplacationEntityException.class, ()->{
            service.insert(genreDto);
        });
        Mockito.verify(repository, Mockito.times(1)).save(any());
    }


    // Teste para update (sucesso)
    @Test
    void updateShouldReturnGenreDtoWhenValidCredentials() {
        Mockito.when(repository.saveAndFlush(any())).thenReturn(genre);
        GenreDto result = service.update(validId, newGenre);
        assertNotNull(result);
        assertEquals(newGenre.getName(), result.getName());
        assertEquals(GenreDto.class, result.getClass());
        verify(repository, times(1)).findById(validId);
        verify(repository, times(1)).saveAndFlush(any());
    }

    // Teste para update (falha: id nao encontrado))
    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourcesNotFoundException.class, ()->{
            service.update(invalidId, genreDto);
        });
        Mockito.verify(repository, Mockito.times(1)).findById(invalidId);
        Mockito.verify(repository, Mockito.times(0)).save(any());
    }
    // Teste para update (falha: credenciais duplicadas))
    @Test
    void updateShouldThrowDuplicationExceptionWhenInvalidCredentials() {
        Mockito.doThrow(DuplacationEntityException.class).when(repository).saveAndFlush(genre);
        Assertions.assertThrows(DuplacationEntityException.class, ()->{
            service.update(validId, genreDto);
        });
        Mockito.verify(repository, Mockito.times(1)).findById(validId);

    }
}
