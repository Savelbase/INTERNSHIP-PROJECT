package com.rmn.toolkit.bankclient.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.bankclient.event.client.BankClientCreatedEvent;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BankClientCreatedEvent.Payload.class, name = EventTypeConstants.BANK_CLIENT_CREATED)
})
@NoArgsConstructor
public class EventPayload {
}
