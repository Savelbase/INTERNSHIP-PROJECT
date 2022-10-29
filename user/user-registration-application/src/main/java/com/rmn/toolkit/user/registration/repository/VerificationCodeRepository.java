package com.rmn.toolkit.user.registration.repository;

import com.rmn.toolkit.user.registration.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, String> {
    Optional<VerificationCode> findByClientIdAndVerified(String clientId, boolean verified);
}
