package com.rmn.toolkit.deposits.query.repository;

import com.rmn.toolkit.deposits.query.model.Deposit;
import com.rmn.toolkit.deposits.query.model.projection.DepositView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, String> {

    List<DepositView> findAllByClientId(String clientId);
}
