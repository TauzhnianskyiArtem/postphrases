package com.example.coursework.controller;


import com.example.coursework.service.interf.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/restore")
@Controller
public class RestoreController {

    UserService userService;

    @GetMapping
    public String emailValidate() {
        return "restoreAccess";
    }

    @PostMapping
    public String restoreByEmail(@RequestParam String email,
                                 Model model){
        String message = "Message send to your email";
        try {
            userService.restoreByEmail(email);
        } catch (BadCredentialsException e){
            message = e.getMessage();
        }
        model.addAttribute("message",message);
        return "restoreAccess";
    }

    @GetMapping("/password/{code}")
    public String activate(Model model, @PathVariable String code) {
        return "changePassword";
    }

    @PostMapping("/password/{code}")
    public String activate(Model model,
                           @PathVariable String code,
                           @RequestParam String password,
                           @RequestParam String password2) {
        password = password.trim();
        if (password != null && !password.equals(password2))
            model.addAttribute("message", "Passwords are different!");
        else {
            try {
                userService.changePassword(password, code);
                model.addAttribute("message", "Password save");
            } catch (BadCredentialsException e){
                model.addAttribute("message", e.getMessage());
            }
        }
        return "changePassword";
    }
}
