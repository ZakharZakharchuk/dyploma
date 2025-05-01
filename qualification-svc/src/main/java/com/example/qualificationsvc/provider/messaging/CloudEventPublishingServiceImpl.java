package com.example.qualificationsvc.provider.messaging;

import com.example.qualificationsvc.domain.port.CloudEventPublishingService;
import com.example.qualificationsvc.provider.messaging.exception.EventPublishingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudEventPublishingServiceImpl implements CloudEventPublishingService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendEvent(String key, String message, String topic) {
        try {
            kafkaTemplate.send(topic, key, message).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            handlePublishingError(key, topic, e);
        } catch (Exception ex) {
            handlePublishingError(key, topic, ex);
        }
    }

    private void handlePublishingError(String key, String topicName, Exception ex) {
        log.atError()
              .setCause(ex)
              .log("Failed to publish event with key %s to topic %s", key, topicName);
        throw new EventPublishingException("Failed to publish event", ex);
    }
}
