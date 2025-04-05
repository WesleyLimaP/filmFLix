package com.filmFlix.project_filmFlix.dtos.moviesDtos;

import com.filmFlix.project_filmFlix.entities.Movie;

public class MovieInsertDto {
    private Long id;
    private String title;
    private String subTitle;
    private Long year;
    private String imgUrl;
    private String synopsis;
    private Long genre;

    public MovieInsertDto(String title, String subTitle, Long year, String imgUrl, String synopsis, Long genre) {
        this.title = title;
        this.subTitle = subTitle;
        this.year = year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.genre = genre;
    }

    public MovieInsertDto(Long id, String title, String subTitle, Long year, String imgUrl, String synopsis, Long genre) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.year = year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.genre = genre;
    }

    public MovieInsertDto() {
    }

    public MovieInsertDto(Movie entitie) {
        this.id = entitie.getId();
        this.title = entitie.getTitle();
        this.subTitle = entitie.getSubTitle();
        this.year = entitie.getMovieYear();
        this.imgUrl = entitie.getImgUrl();
        this.synopsis = entitie.getSynopsis();
        this.genre = entitie.getGenre().getId();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Long getGenre() {
        return genre;
    }

    public void setGenre(Long genre) {
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }
}
