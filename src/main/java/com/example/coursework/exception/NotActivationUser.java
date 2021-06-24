package com.example.coursework.exception;

import javax.naming.AuthenticationException;

public class NotActivationUser extends AuthenticationException {

    public NotActivationUser(String msg) {
        super(msg);
    }
}
