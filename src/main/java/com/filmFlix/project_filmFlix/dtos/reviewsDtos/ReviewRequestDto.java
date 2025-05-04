package com.filmFlix.project_filmFlix.dtos.reviewsDtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public class ReviewRequestDto {
    private Long moivieId;
    @Size(max = 250)
    private String text;
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;


    public ReviewRequestDto(Long moivieId, String text, Double rating) {
        this.moivieId = moivieId;
        this.text = text;
        this.rating = rating;
    }

    public ReviewRequestDto(String text, Double rating) {
        this.text = text;
        this.rating = rating;
    }

    public ReviewRequestDto(Long moivieId, String text) {
        this.moivieId = moivieId;
        this.text = text;
    }

    public ReviewRequestDto(String text) {
        this.text = text;
    }

    public ReviewRequestDto() {
    }

    public Long getMoivieId() {
        return moivieId;
    }

    public void setMoivieId(Long moivieId) {
        this.moivieId = moivieId;
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
