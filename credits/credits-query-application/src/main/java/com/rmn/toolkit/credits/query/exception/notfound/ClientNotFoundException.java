package com.rmn.toolkit.credits.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientNotFoundException extends ResponseStatusException {
    public ClientNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Client with id='%s' not found", id));
    }
}