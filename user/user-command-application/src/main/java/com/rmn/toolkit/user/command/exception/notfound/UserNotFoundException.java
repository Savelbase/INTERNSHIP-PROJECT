package com.rmn.toolkit.user.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(String userId) {
        super(HttpStatus.NOT_FOUND, String.format("User with userId='%s' not found", userId));
    }
}
