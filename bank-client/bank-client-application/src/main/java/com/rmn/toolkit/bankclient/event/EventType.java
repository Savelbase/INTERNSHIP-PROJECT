package com.rmn.toolkit.bankclient.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType {
    BANK_CLIENT_CREATED(AssociatedObject.CLIENT);

    @Getter
    private final AssociatedObject associatedObject;

    public enum AssociatedObject {
        CLIENT
    }
}
