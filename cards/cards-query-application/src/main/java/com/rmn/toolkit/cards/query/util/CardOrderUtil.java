package com.rmn.toolkit.cards.query.util;

import com.rmn.toolkit.cards.query.dto.response.success.CardOrderDto;
import com.rmn.toolkit.cards.query.exception.notfound.CardOrderNotFoundException;
import com.rmn.toolkit.cards.query.model.CardOrder;
import com.rmn.toolkit.cards.query.repository.CardOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardOrderUtil {
    private final CardOrderRepository cardOrderRepository;

    public CardOrder findCardOrderById(String id) {
        return cardOrderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Card order with id='{}' not found", id);
                    throw new CardOrderNotFoundException(id);
                });
    }

    public CardOrder findCardOrderByIdAndClientId(String id, String clientId) {
        return cardOrderRepository.findByIdAndClientId(id, clientId)
                .orElseThrow(() -> {
                    log.error("Card order with id='{}' not found", id);
                    throw new CardOrderNotFoundException(id);
                });
    }

    public CardOrderDto createCardOrderDto(CardOrder cardOrder){
        return CardOrderDto.builder()
                .id(cardOrder.getId())
                .clientId(cardOrder.getClientId())
                .cardProductId(cardOrder.getCardProductId())
                .status(cardOrder.getStatus())
                .build();
    }
}
