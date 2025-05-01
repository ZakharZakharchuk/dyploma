package com.example.personelservice.domain.port;

public interface CloudEventPublishingService {

    void sendEvent(String key, String message, String topic);
}
