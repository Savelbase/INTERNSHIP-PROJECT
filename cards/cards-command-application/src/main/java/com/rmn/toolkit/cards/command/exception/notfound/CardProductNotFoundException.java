package com.rmn.toolkit.cards.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CardProductNotFoundException extends ResponseStatusException {
    public CardProductNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Card product with id='%s' not found", id));
    }
}

