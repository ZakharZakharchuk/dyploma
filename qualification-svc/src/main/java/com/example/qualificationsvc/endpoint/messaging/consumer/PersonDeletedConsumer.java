package com.example.qualificationsvc.endpoint.messaging.consumer;


import static com.example.qualificationsvc.endpoint.messaging.dto.EventTypes.PERSON_DELETED;

import com.example.qualificationsvc.domain.service.ProjectInfoService;
import com.example.qualificationsvc.domain.service.SkillService;
import com.example.qualificationsvc.endpoint.messaging.dto.CloudEventDto;
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

    private final SkillService skillService;
    private final ProjectInfoService projectInfoService;

    public PersonDeletedConsumer(@Qualifier("cloudEventMapper") ObjectMapper cloudEventMapper,
          Validator validator, SkillService skillService, ProjectInfoService projectInfoService) {
        super(cloudEventMapper, validator);
        this.skillService = skillService;
        this.projectInfoService = projectInfoService;
    }

    @Override
    public void accept(CloudEventDto cloudEventDto) {
        log.debug("PersonUpdated record received with id: {}", cloudEventDto.getId());
        PersonDeletedData personDeletedData = convertToCloudEventData(cloudEventDto);
        skillService.deleteAllByPersonId(personDeletedData.getPersonId());
        projectInfoService.deleteAllByPersonId(personDeletedData.getPersonId());
    }

    @Override
    public String getEventType() {
        return PERSON_DELETED.getEventType();
    }
}
