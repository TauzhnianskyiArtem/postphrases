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

        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Postphrases. Please, visit next link: http://localhost:8080/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );
        sendingService.sendMessage(user, message, "Activation code");

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
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Postphrases. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            sendingService.sendMessage(user, message, "Activation code");

            result += "Email save. Activation code sent to the email";

        }
        userRepository.save(user);


        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void restoreByEmail(String email) {
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new BadCredentialsException("Email don`t exists"));

        user.setRestoreCode(UUID.randomUUID().toString());
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Postphrases. For restore Password, visit next link: http://localhost:8080/restore/password/%s",
                user.getUsername(),
                user.getRestoreCode()
        );
        sendingService.sendMessage(user, message, "Change password");
        userRepository.save(user);
    }

    @Override
    public void changePassword(String password, String code) {
        User user = userRepository.findByRestoreCode(code)
                .orElseThrow(() -> new BadCredentialsException("Not found user"));

        if (password.isEmpty())
            throw new BadCredentialsException("Empty password");
        user.setPassword(passwordEncoder.encode(password));
        user.setRestoreCode(null);
        userRepository.save(user);
    }

}
