package com.rmn.toolkit.deposits.command.repository;

import com.rmn.toolkit.deposits.command.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, String> {
}
