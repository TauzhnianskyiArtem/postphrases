package com.example.coursework.service.interf;

import com.example.coursework.domain.Message;

public interface MessageService {
    void save(Message message);

    Iterable<Message> findAllByFilter(boolean isFiltered, String filter);
}
