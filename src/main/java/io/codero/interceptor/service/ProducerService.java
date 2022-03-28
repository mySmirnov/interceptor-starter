package io.codero.interceptor.service;

import io.codero.interceptor.dto.UrlDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProducerService {
    private final String topic;

    private final KafkaTemplate<String, UrlDto> template;

    public void sendMetric(String url, String method) {
        UrlDto urlDto = new UrlDto(url, method, LocalDateTime.now());
        log.info(topic + ": #### <- Producing message <- {}", urlDto);
        this.template.send(topic, urlDto);
    }
}