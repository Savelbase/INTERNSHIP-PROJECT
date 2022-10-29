package com.rmn.toolkit.credits.command.repository;

import com.rmn.toolkit.credits.command.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository  extends JpaRepository<Credit, String> {
}
