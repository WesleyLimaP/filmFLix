package com.filmFlix.project_filmFlix.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "genre")
    private Set<Movie> movies;

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Genre(String name) {
        this.name = name;
    }

    public Genre(Long id) {
        this.id = id;
    }

    public Genre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

}
