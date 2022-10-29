package com.rmn.toolkit.credits.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CreditProductNotFoundException extends ResponseStatusException {
    public CreditProductNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Credit product with id %s does not exist", id));
    }

    public CreditProductNotFoundException(boolean name, String productName) {
        super(HttpStatus.NOT_FOUND, String.format("Credit product with name %s does not exist", productName));
    }
}