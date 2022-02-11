package io.codero.interceptor.context;

import io.codero.interceptor.config.KafkaProducerConfig;
import io.codero.interceptor.dto.UrlDto;
import io.codero.interceptor.interceptor.MetricInterceptor;
import io.codero.interceptor.service.ProducerService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
@EnableConfigurationProperties(KafkaProducerConfig.class)
public class WebContextAutoConfiguration implements WebMvcConfigurer {
    @Autowired
    private KafkaProducerConfig config;

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrap());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return props;
    }

    public ProducerFactory<String, UrlDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, UrlDto> kafkaTemplateInterceptor() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Interceptor apply to all URLs.
        registry.addInterceptor(new MetricInterceptor(new ProducerService(config.getTopic(), kafkaTemplateInterceptor())));
    }


}
