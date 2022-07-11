package com.ss.nagarro.exception;

import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends Exception {

    public AuthorizationException(String message) {
        super(message);
    }
}
