package com.rmn.toolkit.authorization.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.authorization.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.authorization.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.authorization.event.user.UserDeletedEvent;
import com.rmn.toolkit.authorization.event.user.UserEditedEvent;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientRegisteredEvent.Payload.class, name = EventTypeConstants.CLIENT_REGISTERED),
        @JsonSubTypes.Type(value = UserEditedEvent.Payload.class, name = EventTypeConstants.USER_EDITED),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.Payload.class, name = EventTypeConstants.CLIENT_STATUS_CHANGED),
        @JsonSubTypes.Type(value = UserDeletedEvent.Payload.class, name = EventTypeConstants.USER_DELETED)
})
@NoArgsConstructor
public class EventPayload {
}
