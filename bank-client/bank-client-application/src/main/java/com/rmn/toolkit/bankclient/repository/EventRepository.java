package com.rmn.toolkit.bankclient.repository;

import com.rmn.toolkit.bankclient.event.Event;
import com.rmn.toolkit.bankclient.event.EventPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event<? extends EventPayload>, String> {
}
