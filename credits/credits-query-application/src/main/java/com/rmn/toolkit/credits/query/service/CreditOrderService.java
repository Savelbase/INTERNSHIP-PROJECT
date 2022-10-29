package com.rmn.toolkit.credits.query.service;

import com.rmn.toolkit.credits.query.dto.response.success.CreditOrderDto;
import com.rmn.toolkit.credits.query.model.projection.CreditOrderView;
import com.rmn.toolkit.credits.query.repository.CreditOrderRepository;
import com.rmn.toolkit.credits.query.security.SecurityUtil;
import com.rmn.toolkit.credits.query.util.CreditOrderUtil;
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
public class CreditOrderService {
    private final CreditOrderUtil creditOrderUtil;
    private final CreditOrderRepository creditOrderRepository;
    private final SecurityUtil securityUtil;

    @Transactional(readOnly = true)
    public List<CreditOrderDto> getCreditOrdersByClientId(String clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CreditOrderView> creditOrderViews = creditOrderRepository.getAllByClientId(clientId, pageable);

        List<CreditOrderDto> creditOrders = new ArrayList<>();
        creditOrderViews.forEach(creditOrderView -> creditOrders.add(creditOrderUtil.createCreditOrderDto(creditOrderView)));
        return creditOrders;
    }

    @Transactional(readOnly = true)
    public CreditOrderDto getCreditOrderByIdAndClientId(String creditOrderId, String clientId, String authorizationHeader) {
        CreditOrderView creditOrderView = securityUtil.isCurrentUserRoleIsAdmin(authorizationHeader) ?
                creditOrderUtil.findCreditOrderById(creditOrderId) :
                creditOrderUtil.findCreditOrderByIdAndClientId(creditOrderId, clientId);
        return creditOrderUtil.createCreditOrderDto(creditOrderView);
    }
}
