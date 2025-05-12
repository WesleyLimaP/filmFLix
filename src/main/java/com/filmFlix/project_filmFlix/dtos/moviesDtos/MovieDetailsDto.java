package com.filmFlix.project_filmFlix.dtos.moviesDtos;

import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewDto;
import com.filmFlix.project_filmFlix.entities.Genre;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.entities.Review;
import com.filmFlix.project_filmFlix.projections.MovieDetailsProjection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieDetailsDto {
    private String title;
    private String subTitle;
    private Long year;
    private String imgUrl;
    private String synopsis;
    private Set<GenreDto> genre = new HashSet<>();
    private Double userRating;
    private Set<ReviewDto> reviews = new HashSet<>();


    public MovieDetailsDto(String title, String subTitle, Long year, String imgUrl, String synopsis, Double userRating) {
        this.title = title;
        this.subTitle = subTitle;
        this.year = year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.userRating = userRating;
    }

    public MovieDetailsDto(Movie movie) {
        this.title = movie.getTitle();
        this.subTitle = movie.getSubTitle();
        this.year = movie.getMovieYear();
        this.imgUrl = movie.getImgUrl();
        this.synopsis = movie.getSynopsis();
        this.userRating = movie.getUserRatings();

        reviews.addAll(movie.getReviews().stream().map(ReviewDto::new).toList());
        genre.addAll(movie.getGenres().stream().map(GenreDto::new).toList());


    }

    public MovieDetailsDto() {
    }

    public Set<GenreDto> getGenre() {
        return genre;
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


    public Set<ReviewDto> getReviews() {
        return reviews;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }
}
