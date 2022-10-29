package com.rmn.toolkit.user.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientNotFoundException extends ResponseStatusException {
    public ClientNotFoundException(String clientId) {
        super(HttpStatus.NOT_FOUND, String.format("Client with clientId='%s' not found", clientId));
    }

    public ClientNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Client with given passport number is absent");
    }
}
