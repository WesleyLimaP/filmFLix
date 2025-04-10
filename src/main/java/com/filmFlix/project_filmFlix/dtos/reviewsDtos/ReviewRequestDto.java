package com.filmFlix.project_filmFlix.dtos.reviewsDtos;

import jakarta.validation.constraints.Size;

public record ReviewRequestDto(Long moivieId, @Size(max = 250) @Size(max = 250) String text ) {
    public ReviewRequestDto(String text) {
        this(null, text);
    }
}
