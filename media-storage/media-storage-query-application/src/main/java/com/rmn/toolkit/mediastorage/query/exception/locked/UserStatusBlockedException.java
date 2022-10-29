package com.rmn.toolkit.mediastorage.query.exception.locked;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserStatusBlockedException extends ResponseStatusException {
    public UserStatusBlockedException() {
        super(HttpStatus.LOCKED, "User status is blocked");
    }
}
