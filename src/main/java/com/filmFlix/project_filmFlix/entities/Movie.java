package com.filmFlix.project_filmFlix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;


@Entity
@Table(name = "tb_movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    private String subTitle;
    @NotBlank
    private Integer movieYear;
    @NotBlank
    private String imgUrl;
    @NotBlank
    @Column(columnDefinition = "Text")
    private String synopsis;
    @OneToMany(mappedBy = "movie")
    private Set<Review> reviews;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Movie(Long id, String title, String subTitle, Integer movie_year, String imgUrl, String synopsis, Genre genre) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.movieYear = movie_year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.genre = genre;
    }

    public Movie() {
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

    public Integer getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(Integer movieYear) {
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
