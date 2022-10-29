package com.rmn.toolkit.cards.query.service;

import com.rmn.toolkit.cards.query.dto.response.success.CardRequisitesDto;
import com.rmn.toolkit.cards.query.model.projection.CardRequisitesView;
import com.rmn.toolkit.cards.query.util.CardRequisitesUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardRequisitesService {
    private final CardRequisitesUtil cardRequisitesUtil;

    public CardRequisitesDto getCardRequisitesByCardId(String cardId) {
        CardRequisitesView cardRequisitesView = cardRequisitesUtil.findCardRequisitesViewByCardId(cardId);
        return cardRequisitesUtil.createCardRequisitesDto(cardRequisitesView);
    }
}
