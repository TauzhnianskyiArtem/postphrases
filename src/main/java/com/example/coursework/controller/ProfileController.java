package com.example.coursework.controller;


import com.example.coursework.domain.User;
import com.example.coursework.service.interf.UserService;
import com.example.coursework.userdaetails.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String profileUser(
            @AuthenticationPrincipal MyUserDetails userDetails,
            Model model
    ){
        User user = userDetails.getUser();

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping
    public String changeUser(
            @RequestParam("userId") User user,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ){
        if (email.isEmpty() || username.isEmpty() || password.isEmpty()){
            model.addAttribute("message", "Required fields are empty");
        } else {
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            userService.save(user);

            model.addAttribute("message", "Changes saved");
        }

        model.addAttribute("user", user);
        return "profile";
    }
}
