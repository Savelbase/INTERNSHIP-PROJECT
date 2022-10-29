package com.rmn.toolkit.credits.query.repository;

import com.rmn.toolkit.credits.query.model.PayGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayGraphRepository extends JpaRepository<PayGraph, String> {
}
