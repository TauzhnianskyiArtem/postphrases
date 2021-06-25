package com.example.coursework.service.impl;

import com.example.coursework.domain.User;
import com.example.coursework.service.interf.MailSender;
import com.example.coursework.service.interf.SendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SendingServiceImpl implements SendingService {
    @Autowired
    private MailSender mailSender;

    @Override
    public void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Postphrases. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }
}
