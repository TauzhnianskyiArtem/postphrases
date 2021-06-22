package com.example.coursework.service.interf;

import com.example.coursework.domain.Message;

public interface MessageService {
    Iterable<Message> selectAll();

    Iterable<Message> findByTag(String filter);

    void save(Message message);
}
