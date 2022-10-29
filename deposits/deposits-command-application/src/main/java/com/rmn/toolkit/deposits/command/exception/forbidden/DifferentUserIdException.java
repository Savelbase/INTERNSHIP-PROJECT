package com.rmn.toolkit.deposits.command.exception.forbidden;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DifferentUserIdException extends ResponseStatusException {
    public DifferentUserIdException() {
        super(HttpStatus.FORBIDDEN, "User id doesn't match userId in token");
    }
}
