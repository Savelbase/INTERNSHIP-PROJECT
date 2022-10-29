package com.rmn.toolkit.cards.command.repository;

import com.rmn.toolkit.cards.command.model.CardRequisites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRequisitesRepository extends JpaRepository<CardRequisites, String> {
}

