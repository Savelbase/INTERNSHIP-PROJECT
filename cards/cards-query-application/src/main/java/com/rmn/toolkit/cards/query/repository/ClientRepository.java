package com.rmn.toolkit.cards.query.repository;

import com.rmn.toolkit.cards.query.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
