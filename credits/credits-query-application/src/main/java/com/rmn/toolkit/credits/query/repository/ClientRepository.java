package com.rmn.toolkit.credits.query.repository;

import com.rmn.toolkit.credits.query.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
