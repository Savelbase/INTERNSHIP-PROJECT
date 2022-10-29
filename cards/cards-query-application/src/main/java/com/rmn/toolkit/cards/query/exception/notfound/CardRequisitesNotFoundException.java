package com.rmn.toolkit.cards.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CardRequisitesNotFoundException extends ResponseStatusException {
    public CardRequisitesNotFoundException(String cardId) {
        super(HttpStatus.NOT_FOUND, String.format("Card requisites with cardId='%s' not found", cardId));
    }
}