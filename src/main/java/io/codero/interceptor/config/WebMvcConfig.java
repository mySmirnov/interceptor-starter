package io.codero.interceptor.config;

import io.codero.interceptor.interceptor.MetricInterceptor;
import io.codero.interceptor.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.swing.*;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final String topic;
    private final String bootstrapServers;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Interceptor apply to all URLs.
        registry.addInterceptor(new MetricInterceptor(new ProducerService(topic, new KafkaProducerConfig(bootstrapServers).kafkaTemplate())));
    }
}
