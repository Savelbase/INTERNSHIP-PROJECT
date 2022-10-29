package com.rmn.toolkit.credits.query.message.projector;

import com.rmn.toolkit.credits.query.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.query.model.PayGraph;
import org.springframework.stereotype.Component;

@Component
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
