package com.rmn.toolkit.user.query.repo;

import com.rmn.toolkit.user.query.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , String> {
}
