package com.rmn.toolkit.credits.command.message.projector;

import com.rmn.toolkit.credits.command.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.command.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountProjector {
    public Account project(CreditCreatedEvent event) {
        return event.getPayload().getAccount();
    }
}
