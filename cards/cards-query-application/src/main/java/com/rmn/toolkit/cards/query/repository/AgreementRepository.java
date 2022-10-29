package com.rmn.toolkit.cards.query.repository;

import com.rmn.toolkit.cards.query.model.Agreement;
import com.rmn.toolkit.cards.query.model.type.AgreementType;
import org.apache.commons.codec.language.bm.RuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, String> {

    Optional<Agreement> findByAgreementTypeAndActual(AgreementType agreementType, boolean actual);
}
