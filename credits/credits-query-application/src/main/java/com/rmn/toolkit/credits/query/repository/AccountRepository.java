package com.rmn.toolkit.credits.query.repository;

import com.rmn.toolkit.credits.query.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}
