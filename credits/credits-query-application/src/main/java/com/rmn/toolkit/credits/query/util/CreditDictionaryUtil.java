package com.rmn.toolkit.credits.query.util;

import com.rmn.toolkit.credits.query.dto.response.success.CreditProductDto;
import com.rmn.toolkit.credits.query.dto.response.success.CreditProductAgreementDto;
import com.rmn.toolkit.credits.query.exception.notfound.CreditProductNotFoundException;
import com.rmn.toolkit.credits.query.model.CreditDictionary;
import com.rmn.toolkit.credits.query.repository.CreditDictionaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditDictionaryUtil {
    private final CreditDictionaryRepository creditDictionaryRepository;

    public CreditDictionary findCreditProductById(String id) {
        return creditDictionaryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Credit product with id='{}' not found", id);
                    throw new CreditProductNotFoundException(id);
                });
    }

    public CreditDictionary findCreditProductByName(String name) {
        return creditDictionaryRepository.findCreditProductByName(name)
                .orElseThrow(() -> {
                    log.error("Credit product with name='{}' not found", name);
                    throw new CreditProductNotFoundException(true, name);
                });
    }

    public CreditProductDto createCreditProductDto(CreditDictionary creditDictionary) {
        return CreditProductDto.builder()
                .id(creditDictionary.getId())
                .name(creditDictionary.getName())
                .percent(creditDictionary.getPercent())
                .currency(creditDictionary.getCurrency())
                .minCreditAmount(creditDictionary.getMinCreditAmount())
                .maxCreditAmount(creditDictionary.getMaxCreditAmount())
                .minMonthPeriod(creditDictionary.getMinMonthPeriod())
                .maxMonthPeriod(creditDictionary.getMaxMonthPeriod())
                .earlyRepayment(creditDictionary.isEarlyRepayment())
                .guarantors(creditDictionary.isGuarantors())
                .incomeStatement(creditDictionary.isIncomeStatement())
                .build();
    }

    public CreditProductAgreementDto createCreditProductAgreementDto(CreditDictionary creditDictionary) {
        return CreditProductAgreementDto.builder()
                .agreementText(String.format(creditDictionary.getAgreementText(), creditDictionary.getName(),
                        creditDictionary.getMinCreditAmount(), creditDictionary.getCurrency(),
                        creditDictionary.getMaxCreditAmount(), creditDictionary.getCurrency(),
                        creditDictionary.getPercent(),
                        creditDictionary.getMinMonthPeriod(), creditDictionary.getMaxMonthPeriod()))
                .build();
    }
}
