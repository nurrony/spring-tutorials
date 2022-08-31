package info.nmrony.spring.tutorials.eventsdemo;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class PublisherController {

    private final ApplicationEventPublisher publisher;

    PublisherController(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping
    public ResponseEntity<String> publishEvent() {
        // Async Event
        publisher.publishEvent("I'm Async");

        // @EventListener Annotated and ApplicationListener
        publisher.publishEvent(new UserCreatedEvent(this, "Reza"));
        publisher.publishEvent(new UserRemovedEvent("Reza"));

        // Conditional Listener
        publisher.publishEvent(new UserCreatedEvent(this, "nmrony"));
        publisher.publishEvent(new UserRemovedEvent("nmrony"));
        return ResponseEntity.ok().body("event published");
    }
}
