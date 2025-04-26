package com.example.search.endpoint.messaging.consumer;

import static com.example.search.endpoint.messaging.dto.EventTypes.PERSON_DELETED;

import com.example.search.domain.service.PersonProfileService;
import com.example.search.endpoint.messaging.dto.CloudEventDto;
import com.example.search.endpoint.messaging.dto.PersonDeletedData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonDeletedConsumer extends AbstractCloudEventConsumer<PersonDeletedData>
      implements CloudEventConsumer {

    private final PersonProfileService personProfileService;

    public PersonDeletedConsumer(@Qualifier("cloudEventMapper") ObjectMapper cloudEventMapper,
          Validator validator, PersonProfileService personProfileService) {
        super(cloudEventMapper, validator);
        this.personProfileService = personProfileService;
    }

    @Override
    public void accept(CloudEventDto cloudEventDto) {
        log.debug("PersonUpdated record received with id: {}", cloudEventDto.getId());
        PersonDeletedData personDeletedData = convertToCloudEventData(cloudEventDto);
        personProfileService.deletePersonProfile(personDeletedData.getPersonId());
    }

    @Override
    public String getEventType() {
        return PERSON_DELETED.getEventType();
    }
}
