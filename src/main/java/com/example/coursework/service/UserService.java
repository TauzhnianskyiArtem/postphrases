package com.example.coursework.service;

import com.example.coursework.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    void add(User user);
}
