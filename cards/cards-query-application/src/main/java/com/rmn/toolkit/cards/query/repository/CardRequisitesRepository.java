package com.rmn.toolkit.cards.query.repository;

import com.rmn.toolkit.cards.query.model.CardRequisites;
import com.rmn.toolkit.cards.query.model.projection.CardRequisitesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRequisitesRepository extends JpaRepository<CardRequisites, String> {
    Optional<CardRequisitesView> getByCard_Id(String cardId);
}

