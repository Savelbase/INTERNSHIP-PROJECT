package com.rmn.toolkit.user.query.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.user.query.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.query.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.query.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.query.event.user.UserEditedEvent;
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
        @JsonSubTypes.Type(value = UserEditedEvent.class, name = "USER_EDITED"),
        @JsonSubTypes.Type(value = ClientRegisteredEvent.class, name = "CLIENT_REGISTERED"),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.class, name = "CLIENT_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = NotificationChangeStateEvent.class, name = "NOTIFICATION_CHANGE_STATE"),
        @JsonSubTypes.Type(value = UserDeletedEvent.class, name = "USER_DELETED"),
        @JsonSubTypes.Type(value = ApprovedBankClientEvent.class, name = "APPROVED_BANK_CLIENT")
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
    protected String author;

    protected String context;
    protected Instant time;

    protected Integer version;
    protected String parentId;

    protected T payload;

    public Event(Event<T> event) {
        this.id = event.getId();
        this.type = event.getType();
        this.entityId = event.getEntityId();
        this.author = event.getAuthor();
        this.context = event.getContext();
        this.time = event.getTime();
        this.version = event.getVersion();
        this.parentId = event.getParentId();
        this.payload = event.getPayload();
    }
}
