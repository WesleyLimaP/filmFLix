package com.filmFlix.project_filmFlix.repositories;

import com.filmFlix.project_filmFlix.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u  where u.email = :email")
    UserDetails getByEmail(String email);
}
