package com.example.coursework.service;

import com.example.coursework.domain.Message;

public interface MessageService {
    Iterable<Message> selectAll();

    Iterable<Message> findByTag(String filter);

    void add(Message message);
}
