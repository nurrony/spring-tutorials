package info.nurrony.tutorials.spring.eventrestapi.domain.events;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class Event {
    private final UUID eventId;
    private final LocalDate createAt;

    public Event(LocalDate createAt) {
        this.eventId = UUID.randomUUID();
        this.createAt = createAt;
    }
}
