package com.filmFlix.project_filmFlix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.*;


@Entity
@Table(name = "tb_movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subTitle;
    private Long movieYear;
    private String imgUrl;
    @Column(columnDefinition = "Text")
    private String synopsis;
    @OneToMany(mappedBy = "movie")
    private Set<Review> reviews = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "tb_movie_genre",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();
    private Double userRatings;
    private String trailer;

    public Movie(Long id, String title, String subTitle, Long movie_year, String imgUrl, String synopsis) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.movieYear = movie_year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
    }

    public Movie(String title, String subTitle, Long movie_year, String imgUrl, String synopsis) {
        this.title = title;
        this.subTitle = subTitle;
        this.movieYear = movie_year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
    }

    public Movie(String title, String subTitle, Long movieYear, String imgUrl, String synopsis, String trailer) {
        this.title = title;
        this.subTitle = subTitle;
        this.movieYear = movieYear;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.trailer = trailer;
    }

    public Movie() {
    }

    public Movie(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Review> getReviews() {
        return reviews;
    }

    public Double getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(Double userRatings) {
        this.userRatings = userRatings;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) && Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
