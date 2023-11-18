package info.nurrony.tutorials.spring.eventrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import info.nurrony.tutorials.spring.eventrestapi.domain.entities.EventLog;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

}
