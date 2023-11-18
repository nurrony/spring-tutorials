package info.nurrony.tutorials.spring.eventrestapi.domain.entities;

import info.nurrony.tutorials.spring.eventrestapi.domain.events.EventType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "event_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uniqueId;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private String entity;

    @Builder
    public EventLog(Long id, String uniqueId, EventType type, String entity) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.type = type;
        this.entity = entity;
    }
}
