package com.rmn.toolkit.mediastorage.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("User with id='%s' not found", id));
    }
}
