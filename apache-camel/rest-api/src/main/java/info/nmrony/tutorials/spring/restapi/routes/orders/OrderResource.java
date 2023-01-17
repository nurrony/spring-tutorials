package info.nmrony.tutorials.spring.restapi.routes.orders;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        rest("/orders").description("Order REST service")
                .get("order/{id}").description("Details of an order by id").to("direct:order");
    }

}
