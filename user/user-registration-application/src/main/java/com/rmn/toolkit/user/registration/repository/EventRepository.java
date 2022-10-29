package com.rmn.toolkit.user.registration.repository;

import com.rmn.toolkit.user.registration.event.Event;
import com.rmn.toolkit.user.registration.event.EventPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event<? extends EventPayload>, String> {
}
