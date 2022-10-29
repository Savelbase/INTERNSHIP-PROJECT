package com.rmn.toolkit.user.registration.exception.badrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidMiddleNameException extends ResponseStatusException {
    public InvalidMiddleNameException() {
        super(HttpStatus.BAD_REQUEST, "Client middle name doesn't match");
    }
}
