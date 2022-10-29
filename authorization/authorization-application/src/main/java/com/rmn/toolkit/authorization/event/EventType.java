package com.rmn.toolkit.authorization.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType {
    CLIENT_REGISTERED(AssociatedObject.CLIENT),
    USER_EDITED(AssociatedObject.USER),
    CLIENT_STATUS_CHANGED(AssociatedObject.CLIENT),
    USER_DELETED(AssociatedObject.USER);

    @Getter
    private final AssociatedObject associatedObject;

    public enum AssociatedObject {
        CLIENT,
        USER
    }
}
