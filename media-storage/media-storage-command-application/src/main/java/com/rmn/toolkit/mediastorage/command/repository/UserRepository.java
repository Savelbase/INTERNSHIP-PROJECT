package com.rmn.toolkit.mediastorage.command.repository;

import com.rmn.toolkit.mediastorage.command.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
