package com.rmn.toolkit.credits.query.event.user;

import com.rmn.toolkit.credits.query.event.Event;
import com.rmn.toolkit.credits.query.event.EventPayload;
import lombok.*;
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
