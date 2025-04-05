package com.filmFlix.project_filmFlix.controllers;

import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieDetailsDto;
import com.filmFlix.project_filmFlix.dtos.moviesDtos.MovieInsertDto;
import com.filmFlix.project_filmFlix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@Controller
@RequestMapping(value = "/movies")
public class MovieController {
    @Autowired
    private MovieService service;

    @GetMapping()
    public ResponseEntity<Page<MovieDto>> findAll(
            @RequestParam(name = "genre", defaultValue = "0") Long genreId,
            @PageableDefault(size = 10, sort = "title", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok().body(service.findAll(genreId, pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDetailsDto> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(service.findById(id));
    }
    @PostMapping
    public ResponseEntity<MovieInsertDto> insert(@RequestBody MovieInsertDto dto)
    {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(service.insert(dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<MovieDetailsDto> update(@PathVariable Long id, @RequestBody MovieInsertDto dto)
    {

        return ResponseEntity.ok().body(service.update(id, dto));
    }
}
