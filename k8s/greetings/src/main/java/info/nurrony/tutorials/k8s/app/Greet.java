package info.nurrony.tutorials.k8s.app;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Greet {
    private String shipName;
    private String appName;
}
