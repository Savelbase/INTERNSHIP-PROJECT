package com.rmn.toolkit.deposits.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DepositNotFoundException extends ResponseStatusException {
    public DepositNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Deposit with id='%s' not found", id));
    }
}