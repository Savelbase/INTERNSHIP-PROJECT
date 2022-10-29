package com.rmn.toolkit.deposits.command.message;


import com.rmn.toolkit.deposits.command.event.Event;
import com.rmn.toolkit.deposits.command.event.EventPayload;

public interface EventSender {
    void send(Event<? extends EventPayload> event);
}