package com.rmn.toolkit.user.query.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType {
    USER_EDITED(AssociatedObject.USER),
    CLIENT_REGISTERED (AssociatedObject.CLIENT),
    CLIENT_STATUS_CHANGED(AssociatedObject.CLIENT),
    NOTIFICATION_CHANGE_STATE(AssociatedObject.USER),
    USER_DELETED(AssociatedObject.USER),
    APPROVED_BANK_CLIENT(AssociatedObject.CLIENT);

    @Getter
    private final AssociatedObject associatedObject;

    public enum AssociatedObject {
        USER,
        CLIENT
    }
}
