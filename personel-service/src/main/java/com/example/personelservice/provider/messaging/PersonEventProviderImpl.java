package com.example.personelservice.provider.messaging;

import com.example.personelservice.config.CloudEventProperties;
import com.example.personelservice.domain.model.Person;
import com.example.personelservice.domain.port.CloudEventPublishingService;
import com.example.personelservice.domain.port.PersonEventProvider;
import com.example.personelservice.provider.messaging.mapper.PersonEventDataMapper;
import com.example.personelservice.provider.messaging.mapper.PersonEventMapper;
import com.example.search.endpoint.messaging.dto.PersonDeletedData;
import com.example.search.endpoint.messaging.dto.PersonUpdatedData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonEventProviderImpl implements PersonEventProvider {

    private final CloudEventPublishingService cloudEventPublishingService;
    private final CloudEventProperties cloudEventProperties;
    private final PersonEventDataMapper personEventDataMapper;
    private final PersonEventMapper personEventMapper;

    @Override
    public void sendPersonUpdatedEvent(Person person) {
        log.debug("Sending PersonUpdated event for personId: {}",
              person.getId());
        PersonUpdatedData data = personEventDataMapper.toUpdatedData(person);
        String cloudEvent = personEventMapper.toPersonUpdateEvent(data);
        cloudEventPublishingService.sendEvent(data.getPersonId(), cloudEvent,
              cloudEventProperties.getPerson().getSendTopic());
        log.debug("PersonUpdated event sent successfully for personId: {}",
              person.getId());
    }

    @Override
    public void sendPersonDeletedEvent(String id) {
        log.debug("Sending PersonDeleted event for personId: {}", id);
        PersonDeletedData data = personEventDataMapper.toDeletedData(id);
        String cloudEvent = personEventMapper.toPersonDeleteEvent(data);
        cloudEventPublishingService.sendEvent(data.getPersonId(), cloudEvent,
              cloudEventProperties.getPerson().getSendTopic());
        log.debug("PersonDeleted event sent successfully for personId: {}", id);
    }
}
