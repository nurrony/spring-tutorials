package info.nurrony.tutorials.spring.eventrestapi.domain.events;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public abstract class AccountEvent extends Event {
    private final EventType eventType;

    public AccountEvent(LocalDate createAt, EventType eventType) {
        super(createAt);
        this.eventType = eventType;
    }
}
