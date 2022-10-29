package com.rmn.toolkit.credits.command.repository;

import com.rmn.toolkit.credits.command.model.CreditDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditDictionaryRepository extends JpaRepository<CreditDictionary, String> {
    Optional<CreditDictionary> findCreditProductByName(String name);
}
