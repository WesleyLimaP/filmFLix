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
    @NotBlank
    private String subTitle;
    @NotBlank
    private Integer date;
    @NotBlank
    private String imgUrl;
    @NotBlank
    private String synopsys;
    @OneToMany(mappedBy = "movie")
    private Set<Review> reviews;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Movie(Long id, String title, String subTitle, Integer date, String imgUrl, String synopsys, Set<Review> reviews, Genre genre) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.date = date;
        this.imgUrl = imgUrl;
        this.synopsys = synopsys;
        this.reviews = reviews;
        this.genre = genre;
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

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSynopsys() {
        return synopsys;
    }

    public void setSynopsys(String synopsys) {
        this.synopsys = synopsys;
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
