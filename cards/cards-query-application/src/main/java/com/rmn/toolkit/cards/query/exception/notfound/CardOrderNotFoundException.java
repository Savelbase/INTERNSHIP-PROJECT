package com.rmn.toolkit.cards.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CardOrderNotFoundException extends ResponseStatusException {
    public CardOrderNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Card order with id='%s' not found", id));
    }
}
