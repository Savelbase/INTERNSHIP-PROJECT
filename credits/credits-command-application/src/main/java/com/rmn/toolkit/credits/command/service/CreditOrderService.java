package com.rmn.toolkit.credits.command.service;

import com.rmn.toolkit.credits.command.dto.request.CreditOrderDto;
import com.rmn.toolkit.credits.command.dto.request.CreditOrderStatusDto;
import com.rmn.toolkit.credits.command.event.EventType;
import com.rmn.toolkit.credits.command.event.credit.CreditOrderCreatedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditOrderStatusChangedEvent;
import com.rmn.toolkit.credits.command.message.EventSender;
import com.rmn.toolkit.credits.command.model.CreditOrder;
import com.rmn.toolkit.credits.command.util.CreditDictionaryUtil;
import com.rmn.toolkit.credits.command.util.CreditOrderUtil;
import com.rmn.toolkit.credits.command.util.EventPayloadUtil;
import com.rmn.toolkit.credits.command.util.EventUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditOrderService {
    private static final int VERSION = 1;

    private final EventSender eventSender;
    private final EventUtil eventUtil;
    private final CreditOrderUtil creditOrderUtil;
    private final CreditDictionaryUtil creditDictionaryUtil;
    private final EventPayloadUtil eventPayloadUtil;

    @Transactional
    public CreditOrder createCreditOrder(String clientId, CreditOrderDto creditOrderDto) {
        creditDictionaryUtil.findCreditProductByName(creditOrderDto.getCreditProductName());
        CreditOrder creditOrder = creditOrderUtil.createCreditOrder(creditOrderDto, clientId);

        CreditOrderCreatedEvent.Payload payload = eventPayloadUtil.createCreditOrderCreatedEventPayload(creditOrder);
        CreditOrderCreatedEvent event = CreditOrderCreatedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.CREDIT_ORDER_CREATED, creditOrder.getId(), VERSION, clientId, payload);
        eventSender.send(event);
        return creditOrder;
    }

    @Transactional
    public void changeCreditOrderStatusById(CreditOrder creditOrder, CreditOrderStatusDto creditOrderStatusDto, String authorId) {
        creditOrder.setStatus(creditOrderStatusDto.getStatusType());

        CreditOrderStatusChangedEvent.Payload payload = eventPayloadUtil.createCreditOrderStatusChangedEvent(creditOrder);
        CreditOrderStatusChangedEvent event = CreditOrderStatusChangedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.CREDIT_ORDER_STATUS_CHANGED, creditOrder.getId(),
                creditOrder.getVersion() + 1, authorId, payload);
        eventSender.send(event);
    }
}
