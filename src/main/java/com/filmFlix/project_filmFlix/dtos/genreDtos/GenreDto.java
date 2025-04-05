package com.filmFlix.project_filmFlix.dtos.genreDtos;

import com.filmFlix.project_filmFlix.entities.Genre;

public class GenreDto{
    Long id;
    String name;

    public GenreDto(Genre x) {
        this.id = x.getId();
        this.name = x.getName();
    }

    public GenreDto() {
    }

    public GenreDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public GenreDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
