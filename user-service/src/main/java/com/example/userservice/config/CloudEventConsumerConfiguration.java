package com.example.userservice.config;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;

import com.example.userservice.domain.exception.ConsumerNonRetryableException;
import com.example.userservice.endpoint.messaging.consumer.CloudEventConsumer;
import com.example.userservice.endpoint.messaging.dto.CloudEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class CloudEventConsumerConfiguration {

    private static final String DELIMITER = ",";
    private final ObjectMapper cloudEventMapper;
    private final CloudEventProperties cloudEventProperties;
    private final Map<String, CloudEventConsumer> consumers;
    private List<String> userEventTypes;
    private List<String> personEventTypes;

    public CloudEventConsumerConfiguration(Collection<CloudEventConsumer> consumers,
          @Qualifier("cloudEventMapper") ObjectMapper cloudEventMapper,
          CloudEventProperties cloudEventProperties) {
        this.consumers = consumers.stream()
              .collect(toMap(CloudEventConsumer::getEventType, Function.identity()));
        this.cloudEventMapper = cloudEventMapper;
        this.cloudEventProperties = cloudEventProperties;
    }

    @KafkaListener(topics = "${messaging.events.user.receive-topic}",
          groupId = "${messaging.events.user.consumer-group-id}")
    public void consumeUserEvent(ConsumerRecord<String, String> record) {
        process(record, userEventTypes);
    }

    @KafkaListener(topics = "${messaging.events.person.receive-topic}",
          groupId = "${messaging.events.person.consumer-group-id}")
    public void consumePersonEvent(ConsumerRecord<String, String> record) {
        process(record, personEventTypes);
    }

    private void process(ConsumerRecord<String, String> record, List<String> eventTypes) {
        CloudEventDto cloudEventDto;
        try {
            cloudEventDto = getCloudEvent(record);
            validate(record, cloudEventDto);
            resolveTypeAndProcess(cloudEventDto, eventTypes);
        } catch (Exception e) {
            log.error("Error processing record: {}", record, e);
            throw e;
        }
    }

    private CloudEventDto getCloudEvent(ConsumerRecord<String, String> record) {
        try {
            return cloudEventMapper.readValue(record.value(), CloudEventDto.class);
        } catch (Exception e) {
            log.error("Error deserializing record: {}", record, e);
            throw new ConsumerNonRetryableException("Error deserializing record", e);
        }
    }

    //TODO Implement normal validation
    private void validate(ConsumerRecord<String, String> record, CloudEventDto cloudEventDto) {
        if (cloudEventDto.getId() == null || cloudEventDto.getId().isEmpty()) {
            throw new ConsumerNonRetryableException("Invalid event ID");
        }
    }

    private void resolveTypeAndProcess(CloudEventDto cloudEventDto, List<String> eventTypes) {
        String eventType = cloudEventDto.getType();
        if (eventTypes.contains(eventType)) {
            CloudEventConsumer consumer = consumers.get(eventType);
            if (consumer != null) {
                consumer.accept(cloudEventDto);
            } else {
                log.warn("No consumer found for event type: {}", eventType);
            }
        } else {
            log.warn("Unsupported event type: {}", eventType);
        }
    }

    @PostConstruct
    protected void loadEventSourcesAndFilters() {
        this.userEventTypes = asList(
              cloudEventProperties.getUser().getEventTypes().split(DELIMITER));
        this.personEventTypes = asList(
              cloudEventProperties.getPerson().getEventTypes().split(DELIMITER));
    }
}
