package info.nurrony.tutorials.k8s.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/greets")
public class GreetResource {

    final private Greet greet;

    @PostConstruct
    public void setGreetings() {
        greet.setAppName("Greeting");
        greet.setShipName("Devship");
    }

    @GetMapping
    public String index() {
        return "Application " + greet.getAppName() + " is running on " + greet.getShipName();
    }

}
