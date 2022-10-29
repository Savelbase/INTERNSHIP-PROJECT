package com.rmn.toolkit.user.registration.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.user.registration.event.client.BankClientCreatedEvent;
import com.rmn.toolkit.user.registration.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.registration.event.user.UserDeletedEvent;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientRegisteredEvent.Payload.class, name = EventTypeConstants.CLIENT_REGISTERED),
        @JsonSubTypes.Type(value = BankClientCreatedEvent.Payload.class, name = EventTypeConstants.BANK_CLIENT_CREATED),
        @JsonSubTypes.Type(value = UserDeletedEvent.Payload.class, name = EventTypeConstants.USER_DELETED)
})
@NoArgsConstructor
public class EventPayload {
}
