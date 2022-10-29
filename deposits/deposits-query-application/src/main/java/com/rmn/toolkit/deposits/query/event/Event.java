package com.rmn.toolkit.deposits.query.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.deposits.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.deposits.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.deposits.query.event.deposit.DepositCreatedEvent;
import com.rmn.toolkit.deposits.query.event.user.UserDeletedEvent;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Instant;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = EventType.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientRegisteredEvent.class, name = "CLIENT_REGISTERED"),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.class, name = "CLIENT_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = UserDeletedEvent.class, name = "USER_DELETED"),
        @JsonSubTypes.Type(value = DepositCreatedEvent.class, name = "DEPOSIT_CREATED"),
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