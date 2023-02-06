package info.nmrony.tutorials.spring.restcrud.configs;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestConfiguration extends RouteBuilder {

    @Value("${server.port}")
    String serverPort;

    @Value("${camel.servlet.mapping.context-path}")
    private String contextPath;

    final private Environment env;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .enableCORS(true)
                .port(env.getProperty("server.port", "8080"))
                .contextPath(contextPath.substring(0, contextPath.length() - 2))
                .bindingMode(RestBindingMode.json)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Camel RESTful CRUD API")
                .apiProperty("api.version", "1.0.0");
    }

}
