package com.rmn.toolkit.user.query.repo;

import com.rmn.toolkit.user.query.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, String> {
    @Query("select c from Client c join User u on c.id = u.id")
    @Override
    Page<Client> findAll(Pageable pageable);
}
