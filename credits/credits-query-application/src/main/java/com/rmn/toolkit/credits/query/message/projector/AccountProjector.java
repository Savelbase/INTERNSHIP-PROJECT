package com.rmn.toolkit.credits.query.message.projector;

import com.rmn.toolkit.credits.query.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.query.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountProjector {
    public Account project(CreditCreatedEvent event) {
        return event.getPayload().getAccount();
    }
}
