package com.rmn.toolkit.user.query.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAccessTokenException extends ResponseStatusException {
    public InvalidAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid access token");
    }
}
