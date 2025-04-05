package com.filmFlix.project_filmFlix.controllers;

import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/genre")
public class GenreController {

    @Autowired
    private GenreService service;

    @GetMapping()
    public ResponseEntity<Page<GenreDto>> findAll(
            @PageableDefault(size = 10, page = 0,  direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<GenreDto> findById(
          @PathVariable Long id)
    {
        return ResponseEntity.ok().body(service.findById(id));
    }
    @PostMapping()
    public ResponseEntity<GenreDto> insert(
          @RequestBody GenreDto dto)
    {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(service.insert(dto));
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<GenreDto> update(@PathVariable Long id, @RequestBody GenreDto dto)
    {

        return ResponseEntity.ok( service.update(id, dto));
    }
}
