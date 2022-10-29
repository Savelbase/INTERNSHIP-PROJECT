package com.rmn.toolkit.cards.command.testUtil;

import com.rmn.toolkit.cards.command.dto.request.CardDailyLimitDto;
import com.rmn.toolkit.cards.command.dto.request.CardOrderDto;
import com.rmn.toolkit.cards.command.dto.request.CardOrderStatusDto;
import com.rmn.toolkit.cards.command.dto.request.CardStatusDto;
import com.rmn.toolkit.cards.command.model.Card;
import com.rmn.toolkit.cards.command.model.CardOrder;
import com.rmn.toolkit.cards.command.model.type.CardOrderStatusType;
import com.rmn.toolkit.cards.command.model.type.CardStatusType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class RequestDtoBuilder {

    public CardOrderDto createCardOrderDto(boolean accepted) {
        return CardOrderDto.builder()
                .cardProductId(EndpointUrlAndConstants.CARD_PRODUCT_ID)
                .acceptedValue(String.valueOf(accepted))
                .build();
    }

    public CardOrderStatusDto createCardOrderStatusDto(CardOrderStatusType type) {
        return CardOrderStatusDto.builder()
                .cardOrderId(EndpointUrlAndConstants.TEST_VALUE)
                .status(type)
                .build();
    }

    public CardOrder createCardOrder() {
        return CardOrder.builder()
                .id(UUID.randomUUID().toString())
                .clientId(EndpointUrlAndConstants.CLIENT_ID)
                .cardProductId(EndpointUrlAndConstants.CARD_PRODUCT_ID)
                .status(CardOrderStatusType.PROCESSING)
                .version(1)
                .build();
    }

    public CardOrderStatusDto createValidCardOrderStatusDto(String id, CardOrderStatusType type) {
        return CardOrderStatusDto.builder()
                .cardOrderId(id)
                .status(type)
                .build();
    }

    public CardStatusDto createCardStatusDto() {
        return CardStatusDto.builder()
                .cardId(EndpointUrlAndConstants.TEST_VALUE)
                .status(CardStatusType.ACTIVE)
                .build();
    }

    public Card createCard() {
        return Card.builder()
                .id(EndpointUrlAndConstants.TEST_VALUE)
                .status(CardStatusType.BLOCKED)
                .build();
    }

    public CardDailyLimitDto createCardDailyLimitDto() {
        return CardDailyLimitDto.builder()
                .cardId(EndpointUrlAndConstants.TEST_VALUE)
                .dailyLimitSum(BigDecimal.ZERO)
                .acceptedValue(String.valueOf(true))
                .build();
    }
}
