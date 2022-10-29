package com.rmn.toolkit.cards.query.service;

import com.rmn.toolkit.cards.query.dto.response.success.AgreementDto;
import com.rmn.toolkit.cards.query.model.Agreement;
import com.rmn.toolkit.cards.query.model.type.AgreementType;
import com.rmn.toolkit.cards.query.util.AgreementUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgreementQueryService {
    private final AgreementUtil agreementUtil;

    @Transactional(readOnly = true)
    public AgreementDto getAgreementText(AgreementType agreementType, boolean actual) {
        Agreement agreement = agreementUtil.findByAgreementType(agreementType, actual);
        return agreementUtil.createAgreementDto(agreement);
    }

}
