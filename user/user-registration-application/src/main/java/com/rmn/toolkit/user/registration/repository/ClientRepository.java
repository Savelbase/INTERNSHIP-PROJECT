package com.rmn.toolkit.user.registration.repository;

import com.rmn.toolkit.user.registration.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    boolean existsByMobilePhoneAndRegistered(String mobilePhone, boolean registered);
    Optional<Client> findByMobilePhone(String mobilePhone);
    boolean existsByVerificationCodeId(String verificationCodeId);
    Optional<Client> findByPassportNumber(String passportNumber);
}
