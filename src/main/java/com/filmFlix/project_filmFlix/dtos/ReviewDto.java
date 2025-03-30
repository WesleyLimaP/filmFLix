package com.filmFlix.project_filmFlix.dtos;

import com.filmFlix.project_filmFlix.projections.MovieInfoProjection;

public class ReviewDto {

    private String text;
    private String userName;

    public ReviewDto(MovieInfoProjection projection) {
        this.text = projection.getText();
        this.userName = projection.getName();
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
