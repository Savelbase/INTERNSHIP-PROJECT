package com.rmn.toolkit.cards.command.repository;

import com.rmn.toolkit.cards.command.event.Event;
import com.rmn.toolkit.cards.command.event.EventPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event<? extends EventPayload>, String> {
}
