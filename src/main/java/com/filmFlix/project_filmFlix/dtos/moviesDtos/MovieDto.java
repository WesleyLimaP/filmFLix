package com.filmFlix.project_filmFlix.dtos.moviesDtos;

import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieProjection;

import java.util.HashSet;
import java.util.Set;

public class MovieDto {
    private String title;
    private String sub_title;
    private Long movie_year;
    private String img_url;
    private Double userRating;
    private String trailer;
    private Set<GenreDto> genres = new HashSet<GenreDto>();


    public MovieDto(MovieProjection projection) {
        this.title = projection.getTitle();
        this.sub_title = projection.getSub_title();
        this.movie_year = projection.getMovie_year();
        this.img_url = projection.getImg_url();
        this.userRating = projection.getUser_ratings();
        this.trailer = projection.getTrailer();
        this.genres.add(new GenreDto(projection.getGenre_Id()));
    }
    public MovieDto(Movie movie) {
        this.title = movie.getTitle();
        this.sub_title = movie.getSubTitle();
        this.movie_year = movie.getMovieYear();
        this.img_url = movie.getImgUrl();
        this.userRating = movie.getUserRatings();
        this.trailer = movie.getTrailer();
        this.genres.addAll(movie.getGenres().stream().map(GenreDto::new).toList());
    }


    public MovieDto() {
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

    public Long getMovie_year() {
        return movie_year;
    }

    public void setMovie_year(Long movie_year) {
        this.movie_year = movie_year;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getTrailer() {
        return trailer;
    }

    public Set<GenreDto> getGenres() {
        return genres;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
