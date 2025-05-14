package com.filmFlix.project_filmFlix.repositories;

import com.filmFlix.project_filmFlix.entities.PasswordRecover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RecoverRepository extends JpaRepository<PasswordRecover, Long> {
    @Query("select obj from PasswordRecover obj where obj.token = :token and obj.expiration > :now")
    Optional<PasswordRecover> findByToken(String token, LocalDateTime now);
}
