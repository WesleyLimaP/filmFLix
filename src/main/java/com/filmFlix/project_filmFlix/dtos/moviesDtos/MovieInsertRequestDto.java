package com.filmFlix.project_filmFlix.dtos.moviesDtos;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class MovieInsertRequestDto {
    @NotBlank
    private String title;
    private String subTitle;
    @NotBlank
    private List<Long> genre = new ArrayList<>();

    public MovieInsertRequestDto(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public MovieInsertRequestDto(String title, String subTitle, List<Long> genre) {
        this.title = title;
        this.subTitle = subTitle;
        this.genre = genre;
    }

    public MovieInsertRequestDto() {
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

    public @NotBlank List<Long> getGenre() {
        return genre;
    }
}
