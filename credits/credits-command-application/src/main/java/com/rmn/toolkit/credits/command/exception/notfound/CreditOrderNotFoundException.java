package com.rmn.toolkit.credits.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CreditOrderNotFoundException extends ResponseStatusException {
    public CreditOrderNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Credit order with id='%s' not found", id));
    }
}

