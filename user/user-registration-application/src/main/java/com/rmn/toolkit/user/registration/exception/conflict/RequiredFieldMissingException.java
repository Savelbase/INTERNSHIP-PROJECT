package com.rmn.toolkit.user.registration.exception.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RequiredFieldMissingException extends ResponseStatusException {
    public RequiredFieldMissingException() {
        super(HttpStatus.CONFLICT, "Required field is missing");
    }
}
