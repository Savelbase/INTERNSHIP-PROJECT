package com.rmn.toolkit.user.query.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExpiredAccessTokenException extends ResponseStatusException {
    public ExpiredAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Access token is expired");
    }
}
