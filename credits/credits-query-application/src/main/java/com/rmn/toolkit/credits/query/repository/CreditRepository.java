package com.rmn.toolkit.credits.query.repository;

import com.rmn.toolkit.credits.query.model.Credit;
import com.rmn.toolkit.credits.query.model.projection.CreditView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit , String> {
    List<CreditView> getAllByAccount_ClientId(String clientId, Pageable pageable);
}
