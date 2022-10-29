package com.rmn.toolkit.deposits.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DepositProductNotFoundException extends ResponseStatusException {
    public DepositProductNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Deposit product with id='%s' not found", id));
    }
}

