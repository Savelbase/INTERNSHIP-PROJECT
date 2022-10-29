package com.rmn.toolkit.credits.command.repository;

import com.rmn.toolkit.credits.command.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
