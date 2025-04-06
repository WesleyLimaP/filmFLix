package com.filmFlix.project_filmFlix.dtos.reviewsDtos;

import com.filmFlix.project_filmFlix.entities.Review;
import com.filmFlix.project_filmFlix.projections.ReviewProjection;

public class ReviewMaxDto {
    Long id;
    String movieTitle;
    String userName;
    String text;

    public ReviewMaxDto(String movieTitle, String userName, String text) {
        this.movieTitle = movieTitle;
        this.userName = userName;
        this.text = text;
    }

    public ReviewMaxDto() {
    }

    public ReviewMaxDto(ReviewProjection projection){
        this.id = projection.getId();
        this.movieTitle = projection.getMovie_title();
        this.userName = projection.getUser_name();
        this.text = projection.getText();
    }
    public ReviewMaxDto(Review review){
        this.id = review.getId();
        this.movieTitle = review.getMovie().getTitle();
        this.userName = review.getUser().getName();
        this.text = review.getText();
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }
}
