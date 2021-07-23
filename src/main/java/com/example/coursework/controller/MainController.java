package com.example.coursework.controller;

import com.example.coursework.domain.Message;
import com.example.coursework.domain.User;
import com.example.coursework.service.interf.MessageService;
import com.example.coursework.service.interf.UserService;
import com.example.coursework.userdaetails.MyUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
@Controller
public class MainController {

    MessageService messageService;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";

    }

    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {
        User user = userDetails.getUser();
        boolean isFiltered = !filter.trim().isEmpty();

        Iterable<Message> messages = messageService.findAllByFilter(isFiltered, filter);
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        model.addAttribute("user", user);
        return "main";
    }

    @SneakyThrows
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file
    ) {

        User user = userDetails.getUser();

        messageService.addMessage(user, text, tag, file);

        return "redirect:/main";
    }

}