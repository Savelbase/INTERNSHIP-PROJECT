package com.rmn.toolkit.cards.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CardReceiptsNotFoundException extends ResponseStatusException {
    public CardReceiptsNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Card receipts with id='%s' not found", id));
    }
}
