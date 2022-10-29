package com.rmn.toolkit.user.registration.exception.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotRFResidentException extends ResponseStatusException {
    public NotRFResidentException() {
        super(HttpStatus.CONFLICT, "Isn't RF resident");
    }

    public NotRFResidentException(boolean residentPassport) {
        super(HttpStatus.CONFLICT, "Not RF resident passport number");
    }
}
