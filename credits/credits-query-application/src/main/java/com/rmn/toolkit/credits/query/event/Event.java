package com.rmn.toolkit.credits.query.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.rmn.toolkit.credits.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.credits.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditOrderCreatedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditOrderStatusChangedEvent;
import com.rmn.toolkit.credits.query.event.user.UserDeletedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = EventType.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditOrderCreatedEvent.class, name = "CREDIT_ORDER_CREATED"),
        @JsonSubTypes.Type(value = CreditOrderStatusChangedEvent.class, name = "CREDIT_ORDER_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = ClientRegisteredEvent.class, name = "CLIENT_REGISTERED"),
        @JsonSubTypes.Type(value = CreditCreatedEvent.class, name = "CREDIT_CREATED"),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.class, name = "CLIENT_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = UserDeletedEvent.class, name = "USER_DELETED")
})
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@SuperBuilder
public class Event<T extends EventPayload> {
    /**
     * Идентификатор события
     */
    @Id
    protected String id;

    /**
     * Тип события
     */
    @Column(nullable = false, insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    protected EventType type;

    /**
     * Id объекта
     */
    @Column
    protected String entityId;

    /**
     * Id пользователя, создавшего событие
     */
    @Column
    protected String authorId;

    /**
     * Контекст события
     */
    @Column
    protected String context;

    /**
     * Время события
     */
    @Column
    protected Instant dateTime;

    /**
     * Версия
     */
    @Column
    protected Integer version;

    /**
     * Если поле заполнено, значит событие возникло не само, а является каскадным
     */
    @Column
    protected String parentId;

    /**
     * Объект
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
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
