package com.rmn.toolkit.user.registration.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType {
    CLIENT_REGISTERED(AssociatedObject.CLIENT),
    BANK_CLIENT_CREATED(AssociatedObject.CLIENT),
    USER_DELETED(AssociatedObject.CLIENT);

    @Getter
    private final AssociatedObject associatedObject;

    public enum AssociatedObject {
        CLIENT,
        USER
    }
}
