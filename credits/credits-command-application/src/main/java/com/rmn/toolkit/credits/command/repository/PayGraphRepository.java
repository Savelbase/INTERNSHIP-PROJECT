package com.rmn.toolkit.credits.command.repository;

import com.rmn.toolkit.credits.command.model.PayGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayGraphRepository extends JpaRepository<PayGraph, String> {
}
