package com.example.coursework.service;

import com.example.coursework.domain.Message;
import com.example.coursework.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("MessageServiceImp")
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Override
    public Iterable<Message> selectAll() {
        return messageRepo.findAll();
    }

    @Override
    public Iterable<Message> findByTag(String filter) {
        return messageRepo.findByTag(filter);
    }

    @Override
    public void add(Message message) {
        messageRepo.save(message);
    }
}
