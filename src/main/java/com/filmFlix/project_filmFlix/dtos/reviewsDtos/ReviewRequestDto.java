package com.filmFlix.project_filmFlix.dtos.reviewsDtos;

public record ReviewRequestDto(Long moivieId, String text ) {
    public ReviewRequestDto(String text) {
        this(null, text);
    }
}
