package com.example.qualificationsvc.domain.port;

public interface CloudEventPublishingService {

    void sendEvent(String key, String message, String topic);
}
