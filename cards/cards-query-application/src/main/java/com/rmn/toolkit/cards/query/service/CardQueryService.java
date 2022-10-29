package com.rmn.toolkit.cards.query.service;

import com.rmn.toolkit.cards.query.dto.response.success.CardDto;
import com.rmn.toolkit.cards.query.model.projection.CardView;
import com.rmn.toolkit.cards.query.util.CardUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardQueryService {
    private final CardUtil cardUtil;

    public CardDto getCardDtoByCardId(String cardId) {
        CardView cardView = cardUtil.findCardViewByCardId(cardId);
        return cardUtil.createCardDto(cardView);
    }

    public List<CardDto> getCardsDtoByClientId(String clientId) {
        List <CardView> cardViewList = cardUtil.findAllByClientId(clientId);
        return cardUtil.createListCardDto(cardViewList);
    }
}
