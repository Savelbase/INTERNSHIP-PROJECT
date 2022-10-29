package com.rmn.toolkit.credits.command.repository;

import com.rmn.toolkit.credits.command.model.CreditOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditOrderRepository extends JpaRepository<CreditOrder, String> {
}
