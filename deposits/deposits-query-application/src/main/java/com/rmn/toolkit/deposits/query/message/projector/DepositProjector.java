package com.rmn.toolkit.deposits.query.message.projector;

import com.rmn.toolkit.deposits.query.event.deposit.DepositCreatedEvent;
import com.rmn.toolkit.deposits.query.model.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositProjector {

    public Deposit project(DepositCreatedEvent event) {
        var payload = event.getPayload();
        return Deposit.builder()
                .id(event.getEntityId())
                .clientId(payload.getClientId())
                .account(payload.getAccount())
                .depositProduct(payload.getDepositProduct())
                .depositAmount(payload.getDepositAmount())
                .endDepositPeriod(payload.getEndDepositPeriod())
                .startDepositPeriod(payload.getStartDepositPeriod())
                .revocable(payload.isRevocable())
                .build();
    }
}
