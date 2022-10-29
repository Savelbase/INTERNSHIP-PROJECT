package com.rmn.toolkit.credits.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CreditOrderNotFoundException extends ResponseStatusException {
    public CreditOrderNotFoundException(String creditOrderId) {
        super(HttpStatus.NOT_FOUND, String.format("Credit order with id %s does not exist", creditOrderId));
    }
}