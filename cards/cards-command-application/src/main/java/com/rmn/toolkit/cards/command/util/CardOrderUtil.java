package com.rmn.toolkit.cards.command.util;

import com.rmn.toolkit.cards.command.dto.request.CardOrderDto;
import com.rmn.toolkit.cards.command.exception.notfound.CardOrderNotFoundException;
import com.rmn.toolkit.cards.command.model.CardOrder;
import com.rmn.toolkit.cards.command.model.type.CardOrderStatusType;
import com.rmn.toolkit.cards.command.repository.CardOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardOrderUtil {
    private static final int VERSION = 1;
    private final CardOrderRepository cardOrderRepository;

    public CardOrder findCardOrderById(String id) {
        return cardOrderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Card order with id='{}' not found", id);
                    throw new CardOrderNotFoundException(id);
                });
    }

    public CardOrder createCardOrder(CardOrderDto cardOrderDto, String clientId) {
        return CardOrder.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .cardProductId(cardOrderDto.getCardProductId())
                .status(CardOrderStatusType.PROCESSING)
                .version(VERSION)
                .build();
    }
}
