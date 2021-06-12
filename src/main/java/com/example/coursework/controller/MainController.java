package com.example.coursework.controller;

import com.example.coursework.domain.Message;
import com.example.coursework.service.MessageService;
import com.example.coursework.userdaetails.MyUserDetails;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class MainController {
    @Autowired
    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting( Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam( required = false) String filter, Model model) {
        Iterable<Message> messages = messageService.selectAll();
        if (filter != null && !filter.isEmpty()) {
            messages = messageService.findByTag(filter);
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @SneakyThrows
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file,
            Model model
    ) {
        Message message = new Message(text, tag, userDetails.getUser());

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
        messageService.add(message);

        Iterable<Message> messages = messageService.selectAll();

        model.addAttribute("messages", messages);

        return "main";
    }

}