package com.example.search.endpoint.messaging.consumer;

import static com.example.search.endpoint.messaging.dto.EventTypes.PERSON_UPDATED;

import com.example.search.domain.service.PersonProfileService;
import com.example.search.endpoint.messaging.dto.CloudEventDto;
import com.example.search.endpoint.messaging.dto.PersonUpdatedData;
import com.example.search.endpoint.messaging.mapper.CloudEventDataMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonUpdatedConsumer extends AbstractCloudEventConsumer<PersonUpdatedData>
      implements CloudEventConsumer {

    private final PersonProfileService personProfileService;
    private final CloudEventDataMapper cloudEventDataMapper;

    public PersonUpdatedConsumer(@Qualifier("cloudEventMapper") ObjectMapper cloudEventMapper,
          Validator validator,
          PersonProfileService personProfileService, CloudEventDataMapper cloudEventDataMapper) {
        super(cloudEventMapper, validator);
        this.personProfileService = personProfileService;
        this.cloudEventDataMapper = cloudEventDataMapper;
    }

    @Override
    public void accept(CloudEventDto cloudEventDto) {
        log.debug("PersonUpdated record received with id: {}", cloudEventDto.getId());
        PersonUpdatedData personUpdatedData = convertToCloudEventData(cloudEventDto);
        personProfileService.applyPersonUpdate(
              cloudEventDataMapper.toModel(personUpdatedData));
    }

    @Override
    public String getEventType() {
        return PERSON_UPDATED.getEventType();
    }
}
