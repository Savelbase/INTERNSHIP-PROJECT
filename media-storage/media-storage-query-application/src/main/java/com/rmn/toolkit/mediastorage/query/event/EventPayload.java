package com.rmn.toolkit.mediastorage.query.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.mediastorage.query.event.avatar.AvatarUploadedEvent;
import com.rmn.toolkit.mediastorage.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.mediastorage.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.mediastorage.query.event.user.UserDeletedEvent;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AvatarUploadedEvent.Payload.class, name = "AVATAR_UPLOADED"),
        @JsonSubTypes.Type(value = ClientRegisteredEvent.Payload.class, name = "CLIENT_REGISTERED"),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.Payload.class, name = "CLIENT_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = UserDeletedEvent.Payload.class, name = "USER_DELETED")
})
@NoArgsConstructor
public class EventPayload {
}
