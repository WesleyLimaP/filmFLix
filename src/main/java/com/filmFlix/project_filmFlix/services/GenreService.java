package com.filmFlix.project_filmFlix.services;

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
            return repository.findById(id).map(GenreDto::new).orElseThrow(()-> new RuntimeException("id nao encontrado"));
        }
        @Transactional
        public GenreDto insert(GenreDto dto){
           Genre genre = new Genre(dto.getName());
            var entitie = repository.save(genre);
            return Stream.of(entitie).map(x -> new GenreDto(entitie)).toList().getFirst();
        }

        @Transactional
        public GenreDto update(Long id, GenreDto dto){
            var entitie = repository.findById(id).orElseThrow(() -> new RuntimeException("id nao encontrado"));

           entitie.setName(dto.getName());
            repository.save(entitie);

            return Stream.of(entitie).map(GenreDto::new).toList().getFirst();
        }
    }


