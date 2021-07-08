package com.example.coursework.service.impl;

import com.example.coursework.domain.Role;
import com.example.coursework.domain.User;
import com.example.coursework.repository.UserRepository;
import com.example.coursework.service.interf.SendingService;
import com.example.coursework.service.interf.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    SendingService sendingService;

    PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean addUser(User user) {
        Optional<User> userByUsername = userRepository.findByUsername(user.getUsername());
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());

        if (userByEmail.isPresent())
            throw new BadCredentialsException("Email already exists");

        if (userByUsername.isPresent())
            throw new BadCredentialsException("Username already exists");

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        sendingService.sendMessage(user);

        return true;
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    public String updateProfile(User user, String password, String email) {
        String result = "";

        String userEmail = user.getEmail();

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
        userRepository.save(user);


        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
