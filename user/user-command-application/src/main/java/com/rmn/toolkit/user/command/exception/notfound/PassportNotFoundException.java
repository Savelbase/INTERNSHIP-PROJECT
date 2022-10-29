package com.rmn.toolkit.user.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PassportNotFoundException extends ResponseStatusException {
    public PassportNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Passport not found");
    }
}
