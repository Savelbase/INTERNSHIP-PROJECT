package com.rmn.toolkit.cards.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CardNotFoundException extends ResponseStatusException {
    public CardNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Card with id='%s' not found", id));
    }
}
