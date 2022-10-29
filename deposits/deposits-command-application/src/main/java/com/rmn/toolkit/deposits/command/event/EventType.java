package com.rmn.toolkit.deposits.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType {
    CLIENT_REGISTERED(AssociatedObject.CLIENT),
    CLIENT_STATUS_CHANGED(AssociatedObject.CLIENT),
    USER_DELETED(AssociatedObject.USER),
    DEPOSIT_CREATED(AssociatedObject.DEPOSIT),
    DEPOSIT_CHANGED(AssociatedObject.DEPOSIT);

    @Getter
    private final AssociatedObject associatedObject;

    public enum AssociatedObject {
        CLIENT,
        USER,
        DEPOSIT
    }
}
