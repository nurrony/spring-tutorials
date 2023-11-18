package info.nurrony.tutorials.spring.eventrestapi.domain.events.publishers;

import java.time.LocalDate;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import info.nurrony.tutorials.spring.eventrestapi.domain.events.CreateAccountEvent;
import info.nurrony.tutorials.spring.eventrestapi.domain.events.EventType;
import info.nurrony.tutorials.spring.eventrestapi.domain.events.LoginAccountEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void pushCreateUserEvent(final Long userId, final String userName) {
        log.info(this.getClass().getName() + ".push create user event start!");
        log.info("userId : " + userId);
        log.info("userName : " + userName);

        publisher.publishEvent(CreateAccountEvent.builder()
                .userName(userName)
                .userId(userId)
                .createAt(LocalDate.now())
                .eventType(EventType.CREATE).build());
    }

    public void pushLoginUserEvent(final String userId, final String userRole) {
        log.info(this.getClass().getName() + ".push login user event Start!");
        log.info("userId : " + userId);
        log.info("userRole : " + userRole);

        publisher.publishEvent(LoginAccountEvent.builder()
                .userId(userId)
                .createAt(LocalDate.now())
                .eventType(EventType.USER_LOGIN)
                .userRole(userRole).build());
    }
}
