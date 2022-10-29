package com.rmn.toolkit.cards.query.util;

import com.rmn.toolkit.cards.query.dto.response.success.CardDto;
import com.rmn.toolkit.cards.query.exception.notfound.CardNotFoundException;
import com.rmn.toolkit.cards.query.model.Card;
import com.rmn.toolkit.cards.query.model.projection.CardProductView;
import com.rmn.toolkit.cards.query.model.projection.CardView;
import com.rmn.toolkit.cards.query.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardUtil {
    private final CardRepository cardRepository;

    public Card findCardByCardId(String cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> {
                    log.error("Card by id='{}' not found", cardId);
                    throw new CardNotFoundException(cardId);
                });
    }

    public CardView findCardViewByCardId(String cardId) {
        return cardRepository.getCardById(cardId)
                .orElseThrow(() -> {
                    log.error("Card by id='{}' not found", cardId);
                    throw new CardNotFoundException(cardId);
                });
    }

    public CardDto createCardDto(CardView cardView) {
        CardProductView cardProductView = cardView.getCardProduct();
        return CardDto.builder()
                .id(cardView.getId())
                .cardName(cardProductView.getCardName())
                .status(cardView.getStatus())
                .type(cardProductView.getType())
                .build();
    }

    public List<CardView> findAllByClientId(String clientId) {
        return cardRepository.findAllByClientId(clientId);
    }

    public List<CardDto> createListCardDto(List<CardView> cardViewList) {
       return cardViewList.stream().map(this::createCardDto).toList();
    }
}
