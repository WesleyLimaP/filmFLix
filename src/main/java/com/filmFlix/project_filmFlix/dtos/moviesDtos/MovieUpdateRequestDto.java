package com.filmFlix.project_filmFlix.dtos.moviesDtos;

import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.entities.Genre;

import java.util.*;

public class MovieUpdateRequestDto {
    private String title;
    private String subTitle;
    private Set<GenreDto> genres = new HashSet<>();
    private Long movieYear;
    private String imgUrl;
    private String synopsis;
    private String trailer;

    public MovieUpdateRequestDto(String title, String subTitle, Long movieYear, String imgUrl, String synopsis, String trailer) {
        this.title = title;
        this.subTitle = subTitle;
        this.movieYear = movieYear;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.trailer = trailer;
    }

    public MovieUpdateRequestDto() {
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

    public Long getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(Long movieYear) {
        this.movieYear = movieYear;
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

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Set<GenreDto> getGenres() {
        return genres;
    }
}
