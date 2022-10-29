package com.rmn.toolkit.cards.query.exception.notfound;

import com.rmn.toolkit.cards.query.model.type.AgreementType;
import org.apache.commons.codec.language.bm.RuleType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AgreementNotFoundException extends ResponseStatusException {
    public AgreementNotFoundException(AgreementType agreementType) {
        super(HttpStatus.NOT_FOUND, String.format("Agreement %s not found", agreementType));
    }
}
