package com.rmn.toolkit.cards.query.repository;

import com.rmn.toolkit.cards.query.model.CardOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardOrderRepository extends JpaRepository<CardOrder, String> {
    List<CardOrder> findAllByClientId(String clientId, Pageable pageable);
    Optional<CardOrder> findByIdAndClientId(String id, String clientId);
}

