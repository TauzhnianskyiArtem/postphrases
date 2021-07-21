package com.example.coursework.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NotActivationEmailException extends UsernameNotFoundException {
    public NotActivationEmailException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NotActivationEmailException(String msg) {
        super(msg);
    }
}
