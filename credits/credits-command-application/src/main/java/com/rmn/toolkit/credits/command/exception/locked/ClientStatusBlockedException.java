package com.rmn.toolkit.credits.command.exception.locked;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientStatusBlockedException extends ResponseStatusException {
    public ClientStatusBlockedException() {
        super(HttpStatus.LOCKED, "Client status is blocked");
    }
}
