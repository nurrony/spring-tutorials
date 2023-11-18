package info.nurrony.tutorials.spring.eventrestapi.domain.events;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public final class CreateAccountEvent extends AccountEvent {
    private final Long userId;
    private final String userName;

    @Builder
    public CreateAccountEvent(LocalDate createAt, EventType eventType, Long userId, String userName) {
        super(createAt, eventType);
        this.userId = userId;
        this.userName = userName;
    }
}
