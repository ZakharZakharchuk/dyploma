package com.example.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties("messaging.events")
public class CloudEventProperties {

    private String eventSourceFilters;
    private Long retryInterval;
    private Long maxAttempts = Long.MAX_VALUE;
    private DomainEventType user;
    private DomainEventType person;

    @Data
    public static class DomainEventType {

        private String receiveTopic;
        private String dlqTopic;
        private String eventTypes;
    }
}
