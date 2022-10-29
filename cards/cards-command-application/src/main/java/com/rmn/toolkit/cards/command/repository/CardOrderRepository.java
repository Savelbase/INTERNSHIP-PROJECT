package com.rmn.toolkit.cards.command.repository;

import com.rmn.toolkit.cards.command.model.CardOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardOrderRepository extends JpaRepository<CardOrder, String> {
}

