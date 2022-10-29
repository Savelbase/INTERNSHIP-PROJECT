package com.rmn.toolkit.cards.query.event.user;

import com.rmn.toolkit.cards.query.event.Event;
import com.rmn.toolkit.cards.query.event.EventPayload;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserDeletedEvent extends Event<UserDeletedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
    }
}
