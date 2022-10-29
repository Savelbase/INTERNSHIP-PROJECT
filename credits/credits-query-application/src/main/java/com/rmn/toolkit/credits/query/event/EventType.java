package com.rmn.toolkit.credits.query.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType {
    CREDIT_ORDER_STATUS_CHANGED(AssociatedObject.CREDIT),
    CREDIT_ORDER_CREATED(AssociatedObject.CREDIT),
    CREDIT_CREATED(AssociatedObject.CREDIT),
    CLIENT_STATUS_CHANGED(AssociatedObject.CLIENT),
    CLIENT_REGISTERED(AssociatedObject.CLIENT),
    USER_DELETED(AssociatedObject.USER);

    @Getter
    private final AssociatedObject associatedObject;

    public enum AssociatedObject {
        CREDIT,
        CLIENT,
        USER
    }
}
