package com.rmn.toolkit.user.command.repository;

import com.rmn.toolkit.user.command.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationCode, String> {
    Optional<VerificationCode> findByClientIdAndVerified(String clientId, boolean verified);
}
