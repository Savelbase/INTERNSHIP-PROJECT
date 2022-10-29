package com.rmn.toolkit.user.command.exception.forbidden;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DifferentPasswordException extends ResponseStatusException {
    public DifferentPasswordException() {
        super(HttpStatus.FORBIDDEN, "Different password exception");
    }
}
