package com.example.coursework.service.interf;

import com.example.coursework.domain.Message;
import com.example.coursework.domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {
    void save(Message message);

    Iterable<Message> findAllByFilter(boolean isFiltered, String filter);

    void addMessage(User user, String text, String tag, MultipartFile file);

}
