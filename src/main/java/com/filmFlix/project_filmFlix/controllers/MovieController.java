package com.filmFlix.project_filmFlix.controllers;

import com.filmFlix.project_filmFlix.dtos.MovieDto;
import com.filmFlix.project_filmFlix.dtos.MovieInfoDto;
import com.filmFlix.project_filmFlix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping(value = "/movies")
public class MovieController {
    @Autowired
    private MovieService service;

    @GetMapping()
    public ResponseEntity<Page<MovieDto>> findAll(
            @RequestParam(name = "genre", defaultValue = "0") Integer genreId,
            @PageableDefault(size = 10, sort = "title", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok().body(service.findAll(genreId, pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieInfoDto> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(service.findById(id));
    }
}
