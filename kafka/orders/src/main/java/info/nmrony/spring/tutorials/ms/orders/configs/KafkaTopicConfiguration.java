package info.nmrony.spring.tutorials.ms.orders.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * KafkaTopicConfiguration
 */
@Configuration
public class KafkaTopicConfiguration {

    @Value("${app.kafka.topic.name}")
    private String topicName;

    // spring bean for kafka topic
    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(topicName)
                .partitions(5)
                .replicas(1)
                .build();
    }
}
