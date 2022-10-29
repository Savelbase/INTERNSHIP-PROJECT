package com.rmn.toolkit.cards.command.repository;

import com.rmn.toolkit.cards.command.model.CardProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardProductRepository extends JpaRepository<CardProduct, String> {
}

