package com.example.coursework.service.impl;

import com.example.coursework.domain.Role;
import com.example.coursework.domain.User;
import com.example.coursework.repos.UserRepo;
import com.example.coursework.service.interf.SendingService;
import com.example.coursework.service.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendingService sendingService;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public boolean addUser(User user) {
        Optional<User> userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb.isPresent()) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendingService.sendMessage(user);

        return true;
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    public String updateProfile(User user, String password, String email) {
        String result = "";

        boolean matches = passwordEncoder.matches(password, user.getPassword());

        String userEmail = user.getEmail();

        if (email == null || email.isEmpty()){
            return "Email is empty";
        }

        if (password != null && !password.isEmpty()) {
            String encodePassword = passwordEncoder.encode(password);
            user.setPassword(encodePassword);
            result = "Password save. ";
        }

        if (!email.equals(userEmail)) {
            user.setEmail(email);

            user.setActivationCode(UUID.randomUUID().toString());
            sendingService.sendMessage(user);

            result += "Email save. Activation code sent to the email";

        }
        userRepo.save(user);


        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
