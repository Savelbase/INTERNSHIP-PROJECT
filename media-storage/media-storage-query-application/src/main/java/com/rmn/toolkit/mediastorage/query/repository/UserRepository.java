package com.rmn.toolkit.mediastorage.query.repository;

import com.rmn.toolkit.mediastorage.query.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
