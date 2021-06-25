package com.example.coursework.service.interf;

import com.example.coursework.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    List<User> findAll();

    void save(User user);

    boolean addUser(User user);

    boolean activateUser(String code);

    String updateProfile(User user, String password, String email);

    Optional<User> findByEmail(String email);
}
