package com.rmn.toolkit.authorization.exception.locked;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApplicationIsLockedException extends ResponseStatusException {
    public ApplicationIsLockedException() {
        super(HttpStatus.LOCKED, "Application is locked. You can unlock it using 'Reset PIN code' button");
    }
}
