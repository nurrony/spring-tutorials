package info.nmrony.tutorials.spring.eventsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EventsDemoApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(EventsDemoApplication.class);
        // uncomment below line if you are not using spring.factories
        // springApplication.addListeners(new SpringBuiltInEventsListener());
        springApplication.run(args);
    }

}
