package info.nmrony.tutorials.spring.cloudconfig.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigTestController {
    @Value("${name}")
    private String name;

    @Value("${profile}")
    private String profile;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxfileSize;

    @GetMapping("/info")
    String getMessage() {
        return "Hello from " + name + " for profile " + profile + " with maxfileSize " + maxfileSize;
    }
}
