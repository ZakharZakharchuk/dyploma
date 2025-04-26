package com.example.search.endpoint.messaging.consumer;

import static com.example.search.endpoint.messaging.dto.EventTypes.QUALIFICATION_UPDATED;

import com.example.search.domain.service.PersonProfileService;
import com.example.search.endpoint.messaging.dto.CloudEventDto;
import com.example.search.endpoint.messaging.dto.QualificationUpdatedData;
import com.example.search.endpoint.messaging.mapper.CloudEventDataMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QualificationUpdatedConsumer extends AbstractCloudEventConsumer<QualificationUpdatedData>
      implements CloudEventConsumer {

    private final PersonProfileService personProfileService;
    private final CloudEventDataMapper cloudEventDataMapper;

    public QualificationUpdatedConsumer(
          @Qualifier("cloudEventMapper") ObjectMapper cloudEventMapper, Validator validator,
          PersonProfileService personProfileService, CloudEventDataMapper cloudEventDataMapper) {
        super(cloudEventMapper, validator);
        this.personProfileService = personProfileService;
        this.cloudEventDataMapper = cloudEventDataMapper;
    }

    @Override
    public void accept(CloudEventDto cloudEventDto) {
        log.debug("PersonUpdated record received with id: {}", cloudEventDto.getId());
        QualificationUpdatedData qualificationUpdatedData = convertToCloudEventData(cloudEventDto);
        personProfileService.applyQuantityUpdate(
              cloudEventDataMapper.toModel(qualificationUpdatedData));
    }

    @Override
    public String getEventType() {
        return QUALIFICATION_UPDATED.getEventType();
    }
}
