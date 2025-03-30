package com.filmFlix.project_filmFlix.dtos;

import com.filmFlix.project_filmFlix.projections.MovieInfoProjection;

import java.util.HashSet;
import java.util.Set;

public class MovieInfoDto {
    private String title;
    private String subTitle;
    private Integer year;
    private String imgUrl;
    private String synopsis;
    private Set<ReviewDto> reviews = new HashSet<>();

    public MovieInfoDto(MovieInfoProjection projection) {
        this.title = projection.getTitle();
        this.subTitle = projection.getSub_title();
        this.year = projection.getMovie_year();
        this.imgUrl = projection.getImg_url();
        this.synopsis = projection.getSynopsis();

    }

    public MovieInfoDto() {
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
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
