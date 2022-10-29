package com.rmn.toolkit.credits.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CreditProductNotFoundException extends ResponseStatusException {
    public CreditProductNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, String.format("Credit product with name='%s' does not exist", name));
    }
}