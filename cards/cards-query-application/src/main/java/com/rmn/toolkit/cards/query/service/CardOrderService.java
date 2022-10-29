package com.rmn.toolkit.cards.query.service;

import com.rmn.toolkit.cards.query.dto.response.success.CardOrderDto;
import com.rmn.toolkit.cards.query.model.CardOrder;
import com.rmn.toolkit.cards.query.repository.CardOrderRepository;
import com.rmn.toolkit.cards.query.security.SecurityUtil;
import com.rmn.toolkit.cards.query.util.CardOrderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardOrderService {
    private final CardOrderUtil cardOrderUtil;
    private final CardOrderRepository cardOrderRepository;
    private final SecurityUtil securityUtil;

    @Transactional(readOnly = true)
    public List<CardOrderDto> getAllCardOrdersByClientId(String clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CardOrder> cardOrders = cardOrderRepository.findAllByClientId(clientId, pageable);

        List<CardOrderDto> creditOrdersDto = new ArrayList<>();
        cardOrders.forEach(cardOrder -> creditOrdersDto.add(cardOrderUtil.createCardOrderDto(cardOrder)));
        return creditOrdersDto;
    }

    @Transactional(readOnly = true)
    public CardOrderDto getCardOrderByIdAndClientId(String cardOrderId, String clientId, String authorizationHeader) {
        CardOrder cardOrder = securityUtil.isCurrentUserRoleIsAdmin(authorizationHeader) ?
                cardOrderUtil.findCardOrderById(cardOrderId) :
                cardOrderUtil.findCardOrderByIdAndClientId(cardOrderId, clientId);
        return cardOrderUtil.createCardOrderDto(cardOrder);
    }
}
