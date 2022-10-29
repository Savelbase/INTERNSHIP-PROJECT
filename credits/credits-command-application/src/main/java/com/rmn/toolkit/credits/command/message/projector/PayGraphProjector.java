package com.rmn.toolkit.credits.command.message.projector;

import com.rmn.toolkit.credits.command.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.command.model.PayGraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayGraphProjector {

    public PayGraph project(CreditCreatedEvent event) {
        var payload = event.getPayload();
        return PayGraph.builder()
                .id(payload.getPayGraphId())
                .creditId(event.getEntityId())
                .paymentList(payload.getPaymentList())
                .build();
    }
}
