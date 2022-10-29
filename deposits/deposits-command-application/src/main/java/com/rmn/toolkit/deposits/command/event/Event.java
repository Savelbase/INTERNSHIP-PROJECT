package com.rmn.toolkit.deposits.command.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.rmn.toolkit.deposits.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.deposits.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.deposits.command.event.deposit.DepositCreatedEvent;
import com.rmn.toolkit.deposits.command.event.user.UserDeletedEvent;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
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
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "events")
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