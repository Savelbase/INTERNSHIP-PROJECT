package com.rmn.toolkit.cards.query.repository;

import com.rmn.toolkit.cards.query.model.CardProduct;
import com.rmn.toolkit.cards.query.model.projection.CardProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardProductRepository extends JpaRepository<CardProduct, String> {

    List<CardProductView> findAllBy ();
    CardProductView getCardProductById (String id);
}

