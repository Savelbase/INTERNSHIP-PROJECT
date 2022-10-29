package com.rmn.toolkit.mediastorage.query.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType {
    AVATAR_UPLOADED(AssociatedObject.MEDIA_FILE),
    CLIENT_REGISTERED(AssociatedObject.CLIENT),
    CLIENT_STATUS_CHANGED(AssociatedObject.CLIENT),
    USER_DELETED(AssociatedObject.USER);

    @Getter
    private final AssociatedObject associatedObject;
    public enum AssociatedObject {
        MEDIA_FILE,
        CLIENT,
        USER
    }
}
