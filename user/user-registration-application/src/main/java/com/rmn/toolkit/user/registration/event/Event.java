package com.rmn.toolkit.user.registration.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.user.registration.event.client.BankClientCreatedEvent;
import com.rmn.toolkit.user.registration.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.registration.event.user.UserDeletedEvent;
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

@Entity
@Table(name = "events")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = EventType.class,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientRegisteredEvent.class, name = EventTypeConstants.CLIENT_REGISTERED),
        @JsonSubTypes.Type(value = BankClientCreatedEvent.class, name = EventTypeConstants.BANK_CLIENT_CREATED),
        @JsonSubTypes.Type(value = UserDeletedEvent.class, name = EventTypeConstants.USER_DELETED)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id")
public class Event<T extends EventPayload> {
    @Id
    @Column(name = "id", nullable = false)
    protected String id;

    @Column(name = "type",
            nullable = false,
            insertable = false,
            updatable = false
    )
    @Enumerated(EnumType.STRING)
    protected EventType type;

    @Column(name = "entity_id", nullable = false)
    protected String entityId;

    @Column(name = "author_id")
    protected String authorId;

    @Column(name = "context", nullable = false)
    protected String context;

    @Column(name = "date_time", nullable = false)
    protected Instant dateTime;

    @Column(name = "parent_id")
    protected String parentId;

    @Column(name = "payload",
            nullable = false,
            columnDefinition = "jsonb"
    )
    @Type(type = "jsonb")
    protected T payload;

    protected Integer version;

    public Event(Event<T> event) {
        this.id = event.getId();
        this.type = event.getType();
        this.entityId = event.getEntityId();
        this.authorId = event.getAuthorId();
        this.context = event.getContext();
        this.dateTime = event.getDateTime();
        this.parentId = event.getParentId();
        this.payload = event.getPayload();
        this.version = event.getVersion();
    }
}
