package com.rmn.toolkit.user.command.repository;

import com.rmn.toolkit.user.command.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,String> {
    Optional<Client> findClientByPassportNumber(String passportNumber);
}
