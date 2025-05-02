package com.example.userservice.endpoint.messaging.consumer;


import static com.example.userservice.endpoint.messaging.dto.EventTypes.USER_CREATED;

import com.example.search.endpoint.messaging.dto.PersonDeletedData;
import com.example.search.endpoint.messaging.dto.UserCreatedData;
import com.example.userservice.domain.service.AuthService;
import com.example.userservice.endpoint.messaging.dto.CloudEventDto;
import com.example.userservice.endpoint.messaging.mapper.CloudEventDataMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCreatedConsumer extends AbstractCloudEventConsumer<UserCreatedData>
      implements CloudEventConsumer {

    private final AuthService authService;
    private final CloudEventDataMapper cloudEventDataMapper;

    public UserCreatedConsumer(@Qualifier("cloudEventMapper") ObjectMapper cloudEventMapper,
          Validator validator, AuthService authService, CloudEventDataMapper cloudEventDataMapper) {
        super(cloudEventMapper, validator);
        this.authService = authService;
        this.cloudEventDataMapper = cloudEventDataMapper;
    }

    @Override
    public void accept(CloudEventDto cloudEventDto) {
        log.debug("UserCreated record received with id: {}", cloudEventDto.getId());
        UserCreatedData personDeletedData = convertToCloudEventData(cloudEventDto);
        authService.register(
              cloudEventDataMapper.toModel(personDeletedData));
    }

    @Override
    public String getEventType() {
        return USER_CREATED.getEventType();
    }
}
