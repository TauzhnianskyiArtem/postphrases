package com.example.coursework.service.interf;

public interface MailSender {
    void send(String emailTo, String subject, String message);
}
