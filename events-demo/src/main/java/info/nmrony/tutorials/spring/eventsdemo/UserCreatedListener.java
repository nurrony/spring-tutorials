package info.nmrony.tutorials.spring.eventsdemo;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
class UserCreatedListener implements ApplicationListener<UserCreatedEvent> {

    @SuppressWarnings("null")
    @Override
    public void onApplicationEvent(UserCreatedEvent event) {
        System.out.println(String.format("User created (ApplicationListener): %s", event.getName()));
    }
}
