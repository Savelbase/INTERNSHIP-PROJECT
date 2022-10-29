package com.rmn.toolkit.user.registration.exception.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BankClientAlreadyExistException extends ResponseStatusException {
    public BankClientAlreadyExistException(String mobilePhone) {
        super(HttpStatus.CONFLICT, String.format("Bank client with mobile phone='%s' already exist", mobilePhone));
    }
}
