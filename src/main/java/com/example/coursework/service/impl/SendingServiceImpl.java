package com.example.coursework.service.impl;

import com.example.coursework.domain.User;
import com.example.coursework.service.interf.MailSender;
import com.example.coursework.service.interf.SendingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class SendingServiceImpl implements SendingService {

    MailSender mailSender;

    @Override
    public void sendMessage(User user, String message, String  subject) {
        if (!user.getEmail().isEmpty()) {

            mailSender.send(user.getEmail(), subject, message);
        }
    }
}
