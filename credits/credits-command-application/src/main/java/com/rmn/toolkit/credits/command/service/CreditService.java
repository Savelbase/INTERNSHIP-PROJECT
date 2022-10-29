package com.rmn.toolkit.credits.command.service;

import com.rmn.toolkit.credits.command.event.EventType;
import com.rmn.toolkit.credits.command.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.command.message.EventSender;
import com.rmn.toolkit.credits.command.model.*;
import com.rmn.toolkit.credits.command.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CreditService {
    private static final int VERSION = 1;
    private static final int SCALE_VALUE = 2;

    private final EventPayloadUtil eventPayloadUtil;
    private final EventUtil eventUtil;
    private final CreditUtil creditUtil;
    private final EventSender eventSender;
    private final CreditDictionaryUtil creditDictionaryUtil;

    public void createCredit(CreditOrder creditOrder) {
        CreditDictionary creditProduct = creditDictionaryUtil.findCreditProductByName(creditOrder.getCreditProductName());
        Account account = creditUtil.createAccount(creditOrder.getClientId());
        Credit credit = creditUtil.createCredit(creditOrder, account.getId(), creditProduct.getId());
        PayGraph payGraph = creditUtil.createPayGraphWithoutPaymentList(credit.getId());
        addPaymentListToPayGraph(payGraph, credit);

        CreditCreatedEvent.Payload payload = eventPayloadUtil.createCreditCreatedEvent(credit, account, payGraph);
        CreditCreatedEvent event = CreditCreatedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.CREDIT_CREATED, credit.getId(), VERSION, account.getClientId(), payload);
        eventSender.send(event);
    }

    private void addPaymentListToPayGraph(PayGraph payGraph, Credit credit) {
        List<Payment> paymentList = new ArrayList<>();
        long monthCount = creditUtil.getMonthCount(credit.getStartCreditPeriod() , credit.getEndCreditPeriod());
        IntStream.range(0 , (int) monthCount)
                .forEach(month -> paymentList.add(
                        Payment.builder()
                                .amount(credit.getCreditAmount().divide(BigDecimal.valueOf(monthCount), SCALE_VALUE, RoundingMode.CEILING))
                                .paymentDate(credit.getStartCreditPeriod().plusMonths(month + 1))
                                .build()
                ));
        payGraph.setPaymentList(paymentList);
    }
}
