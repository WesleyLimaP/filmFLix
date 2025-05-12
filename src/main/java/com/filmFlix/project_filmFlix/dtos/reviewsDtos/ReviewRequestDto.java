package com.filmFlix.project_filmFlix.dtos.reviewsDtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public class ReviewRequestDto {
    @Size(max = 250)
    private String text;
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;


    public ReviewRequestDto(String text, Double rating) {
        this.text = text;
        this.rating = rating;
    }


    public ReviewRequestDto(String text) {
        this.text = text;
    }

    public ReviewRequestDto() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
