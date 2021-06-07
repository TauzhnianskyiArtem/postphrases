package com.example.coursework.controller;

import com.example.coursework.domain.Role;
import com.example.coursework.domain.User;
import com.example.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Optional;

@Controller
public class RegistrationController {
    @Autowired
    @Qualifier("UserServiceImp")
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            User user,
            Model model) {
        Optional<User> userFromDB = userService.findByUsername(user.getUsername());
        if (userFromDB.isPresent()) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userService.add(user);
        return "redirect:/login";
    }
}
