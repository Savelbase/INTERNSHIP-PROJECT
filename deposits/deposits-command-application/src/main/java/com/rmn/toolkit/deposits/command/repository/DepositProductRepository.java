package com.rmn.toolkit.deposits.command.repository;

import com.rmn.toolkit.deposits.command.model.DepositProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositProductRepository extends JpaRepository<DepositProduct, String> {
}
