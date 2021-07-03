package com.example.coursework.controller;


import com.example.coursework.domain.User;
import com.example.coursework.service.interf.UserService;
import com.example.coursework.userdaetails.MyUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileController {

    UserService userService;

    @GetMapping
    public String profileUser(
            @AuthenticationPrincipal MyUserDetails userDetails,
            Model model
    ) {
        User user = userDetails.getUser();

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping
    public String changeUser(
            @RequestParam("userId") User user,
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        String message = userService.updateProfile(user, password, email);

        model.addAttribute("message", message);
        model.addAttribute("user", user);
        return "profile";
    }
}
