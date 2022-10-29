package com.rmn.toolkit.cards.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundStatementException extends ResponseStatusException {

    public NotFoundStatementException(String cardId) {
        super(HttpStatus.NOT_FOUND, String.format("Statement file with cardId='%s' not found", cardId));
    }
}
