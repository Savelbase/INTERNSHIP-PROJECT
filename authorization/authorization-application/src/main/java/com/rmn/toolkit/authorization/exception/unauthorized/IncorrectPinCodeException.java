package com.rmn.toolkit.authorization.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectPinCodeException extends ResponseStatusException {
    public IncorrectPinCodeException(int remainingAttempts) {
        super(HttpStatus.UNAUTHORIZED, String.format("Incorrect PIN code. Remaining attempts='%s'", remainingAttempts));
    }
}
