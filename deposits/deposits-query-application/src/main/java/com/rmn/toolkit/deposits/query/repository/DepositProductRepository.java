package com.rmn.toolkit.deposits.query.repository;


import com.rmn.toolkit.deposits.query.model.DepositProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositProductRepository extends JpaRepository<DepositProduct, String> {
}
