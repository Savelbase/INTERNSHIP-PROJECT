package com.rmn.toolkit.user.registration.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientNotFoundException extends ResponseStatusException {
    public ClientNotFoundException(String clientId) {
        super(HttpStatus.NOT_FOUND, String.format("Client with id='%s' not found", clientId));
    }
}
