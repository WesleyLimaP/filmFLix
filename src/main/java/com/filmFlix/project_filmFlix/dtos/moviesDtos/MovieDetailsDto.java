package com.filmFlix.project_filmFlix.dtos.moviesDtos;

import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewDto;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.entities.Review;
import com.filmFlix.project_filmFlix.projections.MovieDetailsProjection;

import java.util.HashSet;
import java.util.Set;

public class MovieDetailsDto {
    private String title;
    private String subTitle;
    private Long year;
    private String imgUrl;
    private String synopsis;
    private String genre;
    private Set<ReviewDto> reviews = new HashSet<>();

    public MovieDetailsDto(MovieDetailsProjection projection) {
        this.title = projection.getTitle();
        this.subTitle = projection.getSub_title();
        this.year = projection.getMovie_year();
        this.imgUrl = projection.getImg_url();
        this.synopsis = projection.getSynopsis();
        this.genre = projection.getName();

    }

    public MovieDetailsDto(String title, String subTitle, Long year, String imgUrl, String synopsis, String genre) {
        this.title = title;
        this.subTitle = subTitle;
        this.year = year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.genre = genre;
    }

    public MovieDetailsDto(Movie movie) {
        this.title = movie.getTitle();
        this.subTitle = movie.getSubTitle();
        this.year = movie.getMovieYear();
        this.imgUrl = movie.getImgUrl();
        this.synopsis = movie.getSynopsis();
        this.genre = String.valueOf(movie.getGenre().getId());

        for(Review review: movie.getReviews()){
            this.getReviews().add(new ReviewDto(review));
        }

    }

    public MovieDetailsDto() {
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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



}
