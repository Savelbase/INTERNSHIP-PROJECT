package com.rmn.toolkit.deposits.command.message.projector;

import com.rmn.toolkit.deposits.command.event.deposit.DepositCreatedEvent;
import com.rmn.toolkit.deposits.command.model.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositProjector {
    private static final int VERSION = 1;

    public Deposit project(DepositCreatedEvent event) {
        var payload = event.getPayload();
        return Deposit.builder()
                .id(event.getEntityId())
                .clientId(event.getAuthorId())
                .account(payload.getAccount())
                .depositProduct(payload.getDepositProduct())
                .depositAmount(payload.getDepositAmount())
                .endDepositPeriod(payload.getEndDepositPeriod())
                .startDepositPeriod(payload.getStartDepositPeriod())
                .revocable(payload.isRevocable())
                .version(VERSION)
                .build();
    }
}

