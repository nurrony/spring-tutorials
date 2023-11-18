package info.nurrony.tutorials.spring.eventrestapi.domain.events;

import lombok.Getter;

@Getter
public enum EventType {
    CREATE,
    UPDATE,
    DELETE,
    USER_LOGIN
}
