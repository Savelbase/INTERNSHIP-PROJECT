package com.rmn.toolkit.user.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(String userID) {
        super(HttpStatus.NOT_FOUND, String.format("User with id %s does not exist", userID));
    }
}
