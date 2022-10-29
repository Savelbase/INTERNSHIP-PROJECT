package com.rmn.toolkit.authorization.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(String userId) {
        super(HttpStatus.NOT_FOUND, String.format("User with id='%s' not found", userId));
    }

    public UserNotFoundException(String mobilePhone, boolean isMobilePhone) {
        super(HttpStatus.NOT_FOUND, String.format("User with mobile phone='%s' not found", mobilePhone));
    }
}
