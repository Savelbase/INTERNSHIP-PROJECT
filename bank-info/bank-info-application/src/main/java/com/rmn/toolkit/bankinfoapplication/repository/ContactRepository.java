package com.rmn.toolkit.bankinfoapplication.repository;

import com.rmn.toolkit.bankinfoapplication.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact , String> {
}
