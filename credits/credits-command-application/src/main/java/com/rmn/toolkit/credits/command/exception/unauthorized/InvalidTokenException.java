package com.rmn.toolkit.credits.command.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenException extends ResponseStatusException {
    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid token");
    }
}