package com.filmFlix.project_filmFlix.controllers;

import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewMaxDto;
import com.filmFlix.project_filmFlix.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {
    @Autowired
    private ReviewService service;

    @GetMapping(value = "/{movieId}")
    public ResponseEntity<Page<ReviewMaxDto>> findAllByMovie(
            @PageableDefault(size = 10, page = 0,  direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable Long movieId)
    {
        return ResponseEntity.ok().body(service.findAllByMovie(pageable, movieId));
    }
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<GenreDto> findById(
//            @PathVariable Long id)
//    {
//        return ResponseEntity.ok().body(service.findById(id));
//    }
//    @PostMapping()
//    public ResponseEntity<ReviewDto> insert(
//            @RequestBody GenreDto dto)
//    {
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(dto.getId()).toUri();
//        return ResponseEntity.created(uri).body(service.insert(dto));
//    }
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<GenreDto> update(@PathVariable Long id, @RequestBody GenreDto dto)
//    {
//
//        return ResponseEntity.ok( service.update(id, dto));
//    }
}
