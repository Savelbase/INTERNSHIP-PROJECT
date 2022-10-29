package com.rmn.toolkit.user.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VerificationCodeNotFoundException extends ResponseStatusException {
    public VerificationCodeNotFoundException(String clientId) {
        super(HttpStatus.NOT_FOUND, String.format("Verification code for clientId='%s' not found", clientId));
    }
}
