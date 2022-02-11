package io.codero.interceptor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "interceptor")
public class KafkaProducerConfig {
    private String topic;
    private String bootstrap;
}