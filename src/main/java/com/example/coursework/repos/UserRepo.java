package com.example.coursework.repos;

import com.example.coursework.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findDistinctById(Long Id);

}
