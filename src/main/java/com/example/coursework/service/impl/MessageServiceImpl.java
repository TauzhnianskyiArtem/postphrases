package com.example.coursework.service.impl;

import com.example.coursework.domain.Message;
import com.example.coursework.domain.User;
import com.example.coursework.repos.MessageRepo;
import com.example.coursework.service.interf.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service("MessageServiceImp")
public class MessageServiceImpl implements MessageService {

    MessageRepo messageRepo;

    @Value("${upload.path}")
    @NonFinal
    String uploadPath;

    @Override
    public void save(Message message) {
        messageRepo.save(message);
    }

    @Override
    public Iterable<Message> findAllByFilter(boolean isFiltered, String filter) {
        return messageRepo.findAllByFilter(isFiltered, filter);
    }

    @SneakyThrows
    @Override
    public void addMessage(User user, String text, String tag, MultipartFile file) {
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
        messageRepo.save(message);
    }
}
