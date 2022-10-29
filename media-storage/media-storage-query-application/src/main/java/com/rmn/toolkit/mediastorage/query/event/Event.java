package com.rmn.toolkit.mediastorage.query.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.mediastorage.query.event.avatar.AvatarUploadedEvent;
import com.rmn.toolkit.mediastorage.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.mediastorage.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.mediastorage.query.event.user.UserDeletedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = EventType.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AvatarUploadedEvent.class, name = "AVATAR_UPLOADED"),
        @JsonSubTypes.Type(value = ClientRegisteredEvent.class, name = "CLIENT_REGISTERED"),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.class, name = "CLIENT_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = UserDeletedEvent.class, name = "USER_DELETED")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id")
public class Event<T extends EventPayload> {
    protected String id;
    protected EventType type;
    protected String entityId;
    protected String authorId;
    protected String context;
    protected Instant dateTime;
    protected Integer version;
    protected String parentId;
    protected T payload;

    public Event(Event<T> event) {
        this.id = event.getId();
        this.type = event.getType();
        this.entityId = event.getEntityId();
        this.authorId = event.getAuthorId();
        this.context = event.getContext();
        this.dateTime = event.getDateTime();
        this.version = event.getVersion();
        this.parentId = event.getParentId();
        this.payload = event.getPayload();
    }
}