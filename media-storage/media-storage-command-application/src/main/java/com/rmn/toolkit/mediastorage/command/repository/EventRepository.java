package com.rmn.toolkit.mediastorage.command.repository;

import com.rmn.toolkit.mediastorage.command.event.Event;
import com.rmn.toolkit.mediastorage.command.event.EventPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event<? extends EventPayload>, String>  {
}
