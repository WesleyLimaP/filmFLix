package com.filmFlix.project_filmFlix.services;

import com.filmFlix.project_filmFlix.Exceptions.DuplacationEntityException;
import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.entities.Genre;
import com.filmFlix.project_filmFlix.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
public class GenreService {
        @Autowired
        private GenreRepository repository;

        @Transactional
        public Page<GenreDto> findAll(Pageable pageable){
            Page<Genre> pages = repository.findAll(pageable);
            return pages.map(GenreDto::new);
        }

        @Transactional
        public GenreDto findById(Long id){
            return repository.findById(id).map(GenreDto::new).orElseThrow(()-> new ResourcesNotFoundException("id nao encontrado"));
        }
        @Transactional
        public GenreDto insert(GenreDto dto){
            try {
                Genre genre = new Genre(dto.getName());
                var entitie = repository.save(genre);
                return new GenreDto(entitie);
            } catch (Exception e) {
                throw new DuplacationEntityException("categoria ja existe");
            }

        }

        @Transactional
        public GenreDto update(Long id, GenreDto dto){
            try {
                var entitie = repository.findById(id).orElseThrow(() -> new RuntimeException("id nao encontrado"));
                entitie.setName(dto.getName());
                return new GenreDto( repository.save(entitie));
            } catch (Exception e) {
                throw new DuplacationEntityException(e.getMessage());
            }

        }
    }


