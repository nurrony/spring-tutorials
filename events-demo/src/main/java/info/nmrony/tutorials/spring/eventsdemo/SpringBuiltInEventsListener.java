package info.nmrony.tutorials.spring.eventsdemo;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

class SpringBuiltInEventsListener implements ApplicationListener<SpringApplicationEvent>, ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") SpringApplicationEvent event) {
        System.out.println("SpringApplicationEvent Received - " + event);

        // Initializing publisher for user defined event
        this.initPublisher(event);
    }

    @Override
    public void setApplicationContext(@SuppressWarnings("null") ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void initPublisher(SpringApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent) {
            this.applicationContext.getBean(PublisherController.class).publishEvent();
        }
    }
}
