package com.rmn.toolkit.credits.query.repository;

import com.rmn.toolkit.credits.query.model.CreditOrder;
import com.rmn.toolkit.credits.query.model.projection.CreditOrderView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditOrderRepository extends JpaRepository<CreditOrder, String> {
    List<CreditOrderView> getAllByClientId(String clientId, Pageable pageable);
    Optional<CreditOrderView> getProjectionByIdAndClientId(String id, String clientId);
    Optional<CreditOrderView> getProjectionById(String id);
}
