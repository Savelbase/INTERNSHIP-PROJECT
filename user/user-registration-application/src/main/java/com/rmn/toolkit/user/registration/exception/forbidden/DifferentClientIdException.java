package com.rmn.toolkit.user.registration.exception.forbidden;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DifferentClientIdException extends ResponseStatusException {
    public DifferentClientIdException() {
        super(HttpStatus.FORBIDDEN, "Client id doesn't match clientId in token");
    }
}
