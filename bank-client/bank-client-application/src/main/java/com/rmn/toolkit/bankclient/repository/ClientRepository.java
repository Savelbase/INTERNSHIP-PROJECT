package com.rmn.toolkit.bankclient.repository;

import com.rmn.toolkit.bankclient.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
