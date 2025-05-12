package com.filmFlix.project_filmFlix.dtos.moviesDtos;

import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.entities.Movie;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieResponseDto {
    private Long id;
    private String title;
    private String subTitle;
    private Set<GenreDto> genre = new HashSet<>();
    private Long movieYear;
    private String imgUrl;
    private String synopsis;
    private String trailer;


    public MovieResponseDto(Long id, String title, String subTitle, Long movieYear, String imgUrl, String synopsis, String trailer) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.movieYear = movieYear;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.trailer = trailer;
    }

    public MovieResponseDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.subTitle = movie.getSubTitle();
        this.movieYear = movie.getMovieYear();
        this.imgUrl = movie.getImgUrl();
        this.synopsis = movie.getSynopsis();
        this.trailer = movie.getTrailer();
        this.genre.addAll(movie.getGenres().stream().map(GenreDto::new).collect(Collectors.toSet()));
    }

    public MovieResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Set<GenreDto> getGenre() {
        return genre;
    }
}
