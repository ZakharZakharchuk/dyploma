package com.example.userservice.endpoint.messaging.consumer;


import static com.example.userservice.endpoint.messaging.dto.EventTypes.PERSON_DELETED;

import com.example.search.endpoint.messaging.dto.PersonDeletedData;
import com.example.userservice.domain.service.UserService;
import com.example.userservice.endpoint.messaging.dto.CloudEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonDeletedConsumer extends AbstractCloudEventConsumer<PersonDeletedData>
      implements CloudEventConsumer {

    private final UserService userService;

    public PersonDeletedConsumer(@Qualifier("cloudEventMapper") ObjectMapper cloudEventMapper,
          Validator validator, UserService userService) {
        super(cloudEventMapper, validator);
        this.userService = userService;
    }

    @Override
    public void accept(CloudEventDto cloudEventDto) {
        log.debug("PersonUpdated record received with id: {}", cloudEventDto.getId());
        PersonDeletedData personDeletedData = convertToCloudEventData(cloudEventDto);
        userService.deleteUser(personDeletedData.getPersonId());
    }

    @Override
    public String getEventType() {
        return PERSON_DELETED.getEventType();
    }
}
