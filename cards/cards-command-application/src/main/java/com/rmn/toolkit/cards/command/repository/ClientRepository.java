package com.rmn.toolkit.cards.command.repository;

import com.rmn.toolkit.cards.command.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
