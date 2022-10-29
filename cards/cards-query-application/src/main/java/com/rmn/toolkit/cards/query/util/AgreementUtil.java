package com.rmn.toolkit.cards.query.util;

import com.rmn.toolkit.cards.query.dto.response.success.AgreementDto;
import com.rmn.toolkit.cards.query.dto.response.success.CardDto;
import com.rmn.toolkit.cards.query.exception.notfound.AgreementNotFoundException;
import com.rmn.toolkit.cards.query.model.Agreement;
import com.rmn.toolkit.cards.query.model.projection.CardRequisitesView;
import com.rmn.toolkit.cards.query.model.projection.CardView;
import com.rmn.toolkit.cards.query.model.type.AgreementType;
import com.rmn.toolkit.cards.query.repository.AgreementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.language.bm.RuleType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AgreementUtil {
    private final AgreementRepository agreementRepository;

    public Agreement findByAgreementType(AgreementType agreementType, boolean actual) {
        return agreementRepository.findByAgreementTypeAndActual(agreementType, actual)
                .orElseThrow(() -> {
                    log.error("Agreement {} not found", agreementType);
                    throw new AgreementNotFoundException(agreementType);
                });
    }

    public AgreementDto createAgreementDto(Agreement agreement) {
        return AgreementDto.builder()
                .agreementText(agreement.getAgreementText())
                .build();
    }
}

