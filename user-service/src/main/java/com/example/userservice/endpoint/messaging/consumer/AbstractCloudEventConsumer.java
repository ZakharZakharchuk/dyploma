package com.example.userservice.endpoint.messaging.consumer;

import com.example.userservice.domain.exception.ConsumerNonRetryableException;
import com.example.userservice.endpoint.messaging.dto.CloudEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractCloudEventConsumer<T> {

    private final Class<T> aClass;
    private final ObjectMapper cloudEventMapper;

    private final Validator validator;

    @SuppressWarnings("unchecked")
    public AbstractCloudEventConsumer(ObjectMapper cloudEventMapper, Validator validator) {
        this.aClass = (Class<T>) ((ParameterizedType) getClass()
              .getGenericSuperclass()).getActualTypeArguments()[0];
        this.cloudEventMapper = cloudEventMapper;
        this.validator = validator;
    }

    protected T convertToCloudEventData(CloudEventDto cloudEventDto) {
        T eventData;
        try {
            eventData = cloudEventMapper.readValue(cloudEventDto.getData(), aClass);
        } catch (Exception e) {
            log.atError()
                  .setCause(e)
                  .log("Failed to convert CloudEvent data with id: {}", cloudEventDto.getId());
            throw new ConsumerNonRetryableException("Failed to convert CloudEvent data", e);
        }
        validateEventData(eventData);
        return eventData;
    }

    private void validateEventData(T eventData) {
        if (eventData == null) {
            log.atError()
                  .log("CloudEvent data is null");
            throw new ConsumerNonRetryableException("CloudEvent data is null");
        }

        Set<ConstraintViolation<T>> violations = validator.validate(eventData);

        if (!violations.isEmpty()) {

            String violationMessage = violations.stream()
                  .map(v -> String.format("%s %s", v.getMessage(), v.getPropertyPath()))
                  .collect(Collectors.joining("; "));
            log.atError()
                  .log("Failed to validate CloudEvent data: {}", violationMessage);
            throw new ConsumerNonRetryableException(
                  "Failed to validate CloudEvent data" + violationMessage);
        }
    }
}
