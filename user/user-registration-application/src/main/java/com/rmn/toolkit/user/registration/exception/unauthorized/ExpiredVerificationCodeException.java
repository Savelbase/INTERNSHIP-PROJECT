package com.rmn.toolkit.user.registration.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExpiredVerificationCodeException extends ResponseStatusException {
    public ExpiredVerificationCodeException() {
        super(HttpStatus.UNAUTHORIZED, "Verification code is expired");
    }
}
