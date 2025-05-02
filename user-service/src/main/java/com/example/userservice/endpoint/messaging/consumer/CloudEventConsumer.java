package com.example.userservice.endpoint.messaging.consumer;


import com.example.userservice.endpoint.messaging.dto.CloudEventDto;

public interface CloudEventConsumer {

    String DEFAULT_CONSUMER_TYPE = "default";

    void accept(CloudEventDto cloudEventDto);

    default String getEventType() {
        return DEFAULT_CONSUMER_TYPE;
    }
}
