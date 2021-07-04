package com.example.coursework.service.impl;

import com.example.coursework.domain.Message;
import com.example.coursework.repos.MessageRepo;
import com.example.coursework.service.interf.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service("MessageServiceImp")
public class MessageServiceImpl implements MessageService {

    MessageRepo messageRepo;

    @Override
    public void save(Message message) {
        messageRepo.save(message);
    }

    @Override
    public Iterable<Message> findAllByFilter(boolean isFiltered, String filter) {
        return messageRepo.findAllByFilter(isFiltered, filter);
    }
}
