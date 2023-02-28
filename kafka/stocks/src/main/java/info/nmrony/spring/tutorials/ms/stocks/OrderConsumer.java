package info.nmrony.spring.tutorials.ms.stocks;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderConsumer {

    @KafkaListener(topics = "${app.kafka.topic.name}", groupId = "${app.kafka.consumer.group-id}")
    public void consume(OrderEvent event) {
        log.info(String.format("Order event received in stock service => %s", event.toString()));

        // save the order event into the database
    }
}
