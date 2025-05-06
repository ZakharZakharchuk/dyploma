package com.example.personelservice.provider.messaging;

import com.example.personelservice.config.CloudEventProperties;
import com.example.personelservice.domain.model.Person;
import com.example.personelservice.domain.port.CloudEventPublishingService;
import com.example.personelservice.domain.port.UserEventProvider;
import com.example.personelservice.provider.messaging.mapper.PersonEventDataMapper;
import com.example.personelservice.provider.messaging.mapper.PersonEventMapper;
import com.example.personelservice.provider.messaging.mapper.UserCreatedEventMapper;
import com.example.personelservice.provider.messaging.mapper.UserEventDataMapper;
import com.example.search.endpoint.messaging.dto.PersonUpdatedData;
import com.example.search.endpoint.messaging.dto.UserCreatedData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventProviderImpl implements UserEventProvider {

    private final CloudEventPublishingService cloudEventPublishingService;
    private final CloudEventProperties cloudEventProperties;
    private final UserEventDataMapper userEventDataMapper;
    private final UserCreatedEventMapper userCreatedEventMapper;

    @Override
    public void sendUserCreated(Person person, String password, String role) {
        log.debug("Sending UserCreated event for personId: {}",
              person.getId());
        UserCreatedData data = userEventDataMapper.toUserCreatedData(person, password, role);
        String cloudEvent = userCreatedEventMapper.toUserCreatedEvent(data);
        cloudEventPublishingService.sendEvent(data.getPersonId(), cloudEvent,
              cloudEventProperties.getUser().getSendTopic());
        log.debug("UserCreated event sent successfully for personId: {}",
              person.getId());
    }
}
