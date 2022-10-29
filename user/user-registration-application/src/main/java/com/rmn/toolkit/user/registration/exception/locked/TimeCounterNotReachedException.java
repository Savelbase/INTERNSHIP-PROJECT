package com.rmn.toolkit.user.registration.exception.locked;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TimeCounterNotReachedException extends ResponseStatusException {
    public TimeCounterNotReachedException(boolean checkingVerificationCode) {
        super(HttpStatus.LOCKED, "Unavailable check verification code yet");
    }

    public TimeCounterNotReachedException() {
        super(HttpStatus.LOCKED, "Generate new verification code unavailable yet");
    }
}
