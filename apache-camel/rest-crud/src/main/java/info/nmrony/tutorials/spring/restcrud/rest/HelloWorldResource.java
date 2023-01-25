package info.nmrony.tutorials.spring.restcrud.rest;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest().get("/hello")
                .id("hello-resource")
                .description("helloEP", "hello endpoint", "en")
                .to("direct:hello");

        from("direct:hello")
                .log(LoggingLevel.INFO, "${body}")
                .transform().simple("Hello World");

    }

}
