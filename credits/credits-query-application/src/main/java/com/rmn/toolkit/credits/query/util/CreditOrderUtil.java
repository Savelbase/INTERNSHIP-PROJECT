package com.rmn.toolkit.credits.query.util;

import com.rmn.toolkit.credits.query.dto.response.success.CreditOrderDto;
import com.rmn.toolkit.credits.query.exception.notfound.CreditOrderNotFoundException;
import com.rmn.toolkit.credits.query.model.projection.CreditDictionaryView;
import com.rmn.toolkit.credits.query.model.projection.CreditOrderView;
import com.rmn.toolkit.credits.query.repository.CreditOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditOrderUtil {
    private final CreditOrderRepository creditOrderRepository;

    public CreditOrderView findCreditOrderByIdAndClientId(String id, String clientId) {
        return creditOrderRepository.getProjectionByIdAndClientId(id, clientId)
                .orElseThrow(() -> {
                    log.error("Credit order with id='{}' not found", id);
                    throw new CreditOrderNotFoundException(id);
                });
    }

    public CreditOrderView findCreditOrderById(String id) {
        return creditOrderRepository.getProjectionById(id)
                .orElseThrow(() -> {
                    log.error("Credit order with id='{}' not found", id);
                    throw new CreditOrderNotFoundException(id);
                });
    }

    public CreditOrderDto createCreditOrderDto(CreditOrderView creditOrderView){
        CreditDictionaryView creditProduct = creditOrderView.getCreditProduct();
        return CreditOrderDto.builder()
                .id(creditOrderView.getId())
                .creditProductName(creditProduct.getName())
                .status(creditOrderView.getStatus())
                .creditAmount(creditOrderView.getCreditAmount())
                .currency(creditProduct.getCurrency())
                .build();
    }
}
