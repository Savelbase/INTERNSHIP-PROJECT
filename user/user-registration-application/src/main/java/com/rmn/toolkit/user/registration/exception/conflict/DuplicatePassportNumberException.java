package com.rmn.toolkit.user.registration.exception.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicatePassportNumberException extends ResponseStatusException {
    public DuplicatePassportNumberException() {
        super(HttpStatus.CONFLICT, "Passport number should be unique");
    }
}
