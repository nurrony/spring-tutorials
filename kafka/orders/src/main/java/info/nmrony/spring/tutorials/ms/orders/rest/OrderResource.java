package info.nmrony.spring.tutorials.ms.orders.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.spring.tutorials.ms.orders.domain.Order;
import info.nmrony.spring.tutorials.ms.orders.domain.OrderEvent;
import info.nmrony.spring.tutorials.ms.orders.kafka.OrderProducer;

@RestController
@RequestMapping("/api/v1")
public class OrderResource {

    private OrderProducer orderProducer;

    public OrderResource(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order) {

        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is in pending state");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);

        return "Order placed successfully ...";
    }
}
