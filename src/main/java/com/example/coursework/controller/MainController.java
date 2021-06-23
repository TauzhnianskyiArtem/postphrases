package com.example.coursework.controller;

import com.example.coursework.domain.Message;
import com.example.coursework.domain.User;
import com.example.coursework.oauth.UserOAuth2User;
import com.example.coursework.service.interf.MessageService;
import com.example.coursework.service.interf.UserService;
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

    @Autowired
    private UserService userService;

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
            @AuthenticationPrincipal UserOAuth2User userOAuth,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file,
            Model model
    ) {

        User user = null;
        if (userDetails == null){
            user = userService.findByEmail(userOAuth.getEmail()).get();
        }else {
            user = userDetails.getUser();
        }
        Message message = new Message(text, tag, user);

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

        Iterable<Message> messages = messageService.selectAll();

        model.addAttribute("messages", messages);

        return "main";
    }

}