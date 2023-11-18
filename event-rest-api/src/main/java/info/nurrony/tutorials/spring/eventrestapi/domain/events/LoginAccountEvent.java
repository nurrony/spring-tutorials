package info.nurrony.tutorials.spring.eventrestapi.domain.events;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public final class LoginAccountEvent extends AccountEvent {
    private final String userId;
    private final String userRole;

    @Builder
    public LoginAccountEvent(LocalDate createAt, EventType eventType, String userId, String userRole) {
        super(createAt, eventType);
        this.userId = userId;
        this.userRole = userRole;
    }
}
