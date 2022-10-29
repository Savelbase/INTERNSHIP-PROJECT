package com.rmn.toolkit.cards.query.repository;

import com.rmn.toolkit.cards.query.model.Card;
import com.rmn.toolkit.cards.query.model.projection.CardView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    Optional<CardView> getCardById(String id);
    List<CardView> findAllByClientId(String clientId);
}
