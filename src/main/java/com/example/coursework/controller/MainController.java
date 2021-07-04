package com.example.coursework.controller;

import com.example.coursework.domain.Message;
import com.example.coursework.domain.User;
import com.example.coursework.service.interf.MessageService;
import com.example.coursework.userdaetails.MyUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
public class MainController {

    MessageService messageService;

    @Value("${upload.path}")
    @NonFinal
    String uploadPath;


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

        Message message = Message.builder()
                .text(text)
                .author(user)
                .tag(tag)
                .build();

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFileName(resultFilename);
        }
        messageService.save(message);

        return "redirect:/main";
    }

}