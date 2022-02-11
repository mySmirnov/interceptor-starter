package io.codero.interceptor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "interceptor.kafka")
public class KafkaProducerConfig {
    private String bootstrap;
    private String topic;
}