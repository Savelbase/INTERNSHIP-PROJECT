package com.rmn.toolkit.user.query.event.client;

import com.rmn.toolkit.user.query.event.Event;
import com.rmn.toolkit.user.query.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ApprovedBankClientEvent extends Event<ApprovedBankClientEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private boolean bankClient;
    }
}
