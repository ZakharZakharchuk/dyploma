package com.example.qualificationsvc.endpoint.messaging.consumer;


import com.example.qualificationsvc.endpoint.messaging.dto.CloudEventDto;

public interface CloudEventConsumer {

    String DEFAULT_CONSUMER_TYPE = "default";

    void accept(CloudEventDto cloudEventDto);

    default String getEventType() {
        return DEFAULT_CONSUMER_TYPE;
    }
}
