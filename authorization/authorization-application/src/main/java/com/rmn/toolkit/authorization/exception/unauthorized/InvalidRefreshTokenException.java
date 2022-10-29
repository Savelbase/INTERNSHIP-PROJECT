package com.rmn.toolkit.authorization.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRefreshTokenException extends ResponseStatusException {
    public InvalidRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }
}
