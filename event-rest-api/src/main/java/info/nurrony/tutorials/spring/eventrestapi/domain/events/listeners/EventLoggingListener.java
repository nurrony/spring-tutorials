package info.nurrony.tutorials.spring.eventrestapi.domain.events.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import info.nurrony.tutorials.spring.eventrestapi.domain.entities.EventLog;
import info.nurrony.tutorials.spring.eventrestapi.domain.events.AccountEvent;
import info.nurrony.tutorials.spring.eventrestapi.repositories.EventLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventLoggingListener {
    private final EventLogRepository eventRepository;

    @EventListener
    public void loggingAccountEvent(AccountEvent event) {
        log.info(this.getClass().getName() + ".account event logging Start!");

        eventRepository.save(
                EventLog.builder()
                        .type(event.getEventType())
                        .uniqueId(event.getEventId().toString())
                        .entity("ACCOUNT_EVENT")
                        .build());
    }
}
