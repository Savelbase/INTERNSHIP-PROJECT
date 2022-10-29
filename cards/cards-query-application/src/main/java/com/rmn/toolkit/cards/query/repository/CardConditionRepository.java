package com.rmn.toolkit.cards.query.repository;

import com.rmn.toolkit.cards.query.model.CardConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardConditionRepository extends JpaRepository<CardConditions , String> {
}
