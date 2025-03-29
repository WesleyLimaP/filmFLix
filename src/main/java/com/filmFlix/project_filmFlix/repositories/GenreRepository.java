package com.filmFlix.project_filmFlix.repositories;

import com.filmFlix.project_filmFlix.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
