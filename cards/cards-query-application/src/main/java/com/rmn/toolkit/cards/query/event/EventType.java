package com.rmn.toolkit.cards.query.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType {
    CLIENT_REGISTERED(AssociatedObject.CLIENT),
    CLIENT_STATUS_CHANGED(AssociatedObject.CLIENT),
    USER_DELETED(AssociatedObject.USER),
    CARD_ORDER_CREATED(AssociatedObject.CARD),
    CARD_ORDER_STATUS_CHANGED(AssociatedObject.CARD),
    CARD_CREATED(AssociatedObject.CARD),
    CARD_STATUS_CHANGED(AssociatedObject.CARD),
    CARD_DAILY_LIMIT_CHANGED(AssociatedObject.CARD);

    @Getter
    private final AssociatedObject associatedObject;

    public enum AssociatedObject {
        CLIENT,
        USER,
        CARD
    }
}
