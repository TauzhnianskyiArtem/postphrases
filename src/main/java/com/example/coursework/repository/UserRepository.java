package com.example.coursework.repository;

import com.example.coursework.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findDistinctById(Long Id);

    User findByActivationCode(String code);

    Optional<User> findByEmail(String email);
}
