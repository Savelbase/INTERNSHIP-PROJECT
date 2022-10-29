package com.rmn.toolkit.user.command.exception.locked;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MaxLimitExceededException extends ResponseStatusException {
    public MaxLimitExceededException(int timeCounterAfterMaxAttemptSec) {
        super(HttpStatus.LOCKED,
                String.format("Too many unsuccessful attempts, please try after '%s' seconds", timeCounterAfterMaxAttemptSec));
    }
}
