package com.filmFlix.project_filmFlix.dtos;

import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieProjection;

public class MovieDto {
    private String title;
    private String sub_title;
    private Integer movie_year;
    private String img_url;

    public MovieDto(String title, String sub_title, Integer movie_year, String img_url) {
        this.title = title;
        this.sub_title = sub_title;
        this.movie_year = movie_year;
        this.img_url = img_url;
    }

    public MovieDto(MovieProjection projection) {
        this.title = projection.getTitle();
        this.sub_title = projection.getSub_title();
        this.movie_year = projection.getMovie_year();
        this.img_url = projection.getImg_url();
    }

    public MovieDto() {
    }

    public MovieDto(Movie movie) {
        this.title = movie.getTitle();
        this.sub_title = movie.getSubTitle();
        this.movie_year = getMovie_year();
        this.img_url = getImg_url();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public Integer getMovie_year() {
        return movie_year;
    }

    public void setMovie_year(Integer movie_year) {
        this.movie_year = movie_year;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
