package com.filmFlix.project_filmFlix.controllers;

import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewMaxDto;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewRequestDto;
import com.filmFlix.project_filmFlix.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/review")
@EnableMethodSecurity
public class ReviewController {
    @Autowired
    private ReviewService service;

    @GetMapping(value = "/movies/{movieId}")
    public ResponseEntity<Page<ReviewMaxDto>> findAllByMovie(
            @PageableDefault(size = 10, page = 0,  direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable Long movieId)
    {
        return ResponseEntity.ok().body(service.findAllByMovie(pageable, movieId));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ReviewMaxDto> findById(
            @PathVariable Long id)
    {
        return ResponseEntity.ok().body(service.findById(id));
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER') or hasAuthority('ROLE_ADM')")
    public ResponseEntity<ReviewMaxDto> insert(@PathVariable Long id, @Valid @RequestBody ReviewRequestDto dto)
    {
        var review = service.insert(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(review.getId()).toUri();
        return ResponseEntity.created(uri).body(review);
    }
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER') or hasAuthority('ROLE_ADM')")
    public ResponseEntity<ReviewMaxDto> update(@PathVariable Long id, @RequestBody ReviewRequestDto dto)
    {

        return ResponseEntity.ok( service.update(id, dto));
    }
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER') or hasAuthority('ROLE_ADM')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent( ).build();
    }
}
