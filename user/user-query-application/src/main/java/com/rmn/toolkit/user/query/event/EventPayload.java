package com.rmn.toolkit.user.query.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.user.query.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.query.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.query.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.query.event.user.UserEditedEvent;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserEditedEvent.Payload.class, name = "USER_EDITED"),
        @JsonSubTypes.Type(value = ClientRegisteredEvent.Payload.class, name = "CLIENT_REGISTERED"),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.Payload.class, name = "CLIENT_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = NotificationChangeStateEvent.Payload.class, name = "NOTIFICATION_CHANGE_STATE"),
        @JsonSubTypes.Type(value = UserDeletedEvent.Payload.class, name = "USER_DELETED"),
        @JsonSubTypes.Type(value = ApprovedBankClientEvent.Payload.class, name = "APPROVED_BANK_CLIENT")
})
@NoArgsConstructor
public class EventPayload {
}
