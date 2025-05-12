package com.filmFlix.project_filmFlix.dtos.reviewsDtos;

import com.filmFlix.project_filmFlix.entities.Review;
import com.filmFlix.project_filmFlix.projections.MovieDetailsProjection;

import java.util.Objects;

public class ReviewDto {

    private String text;
    private String userName;

    public ReviewDto(MovieDetailsProjection projection) {
        this.text = projection.getText();
        this.userName = projection.getName();
    }
    public ReviewDto(Review review) {
        this.text = review.getText();
        this.userName = review.getUser().getName();
    }

    public ReviewDto(String text, String userName) {
        this.text = text;
        this.userName = userName;
    }

    public ReviewDto() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
