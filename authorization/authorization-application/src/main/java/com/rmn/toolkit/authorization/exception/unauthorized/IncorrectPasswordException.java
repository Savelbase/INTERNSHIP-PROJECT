package com.rmn.toolkit.authorization.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectPasswordException extends ResponseStatusException {
    public IncorrectPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "Incorrect password");
    }
}
