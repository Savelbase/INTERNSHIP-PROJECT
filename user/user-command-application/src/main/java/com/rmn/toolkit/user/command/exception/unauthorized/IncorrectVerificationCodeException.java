package com.rmn.toolkit.user.command.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectVerificationCodeException extends ResponseStatusException {
    public IncorrectVerificationCodeException(int remainingAttempts) {
        super(HttpStatus.UNAUTHORIZED,
                String.format("Incorrect verification code. Remaining attempts='%s'", remainingAttempts));
    }
}
