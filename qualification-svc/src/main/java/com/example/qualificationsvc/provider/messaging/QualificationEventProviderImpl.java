package com.example.qualificationsvc.provider.messaging;

import com.example.qualificationsvc.config.CloudEventProperties;
import com.example.qualificationsvc.domain.model.QualificationProfile;
import com.example.qualificationsvc.domain.port.CloudEventPublishingService;
import com.example.qualificationsvc.domain.port.QualificationEventProvider;
import com.example.qualificationsvc.provider.messaging.mapper.QualificationEventDataMapper;
import com.example.qualificationsvc.provider.messaging.mapper.QualificationEventMapper;
import com.example.search.endpoint.messaging.dto.QualificationUpdatedData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QualificationEventProviderImpl implements QualificationEventProvider {

    private final CloudEventPublishingService cloudEventPublishingService;
    private final CloudEventProperties cloudEventProperties;
    private final QualificationEventDataMapper qualificationEventDataMapper;
    private final QualificationEventMapper qualificationEventMapper;

    @Override
    public void sendQualificationEvent(QualificationProfile qualificationProfile) {
        log.debug("Sending qualification event for personId: {}",
              qualificationProfile.getPersonId());
        QualificationUpdatedData data = qualificationEventDataMapper.toData(qualificationProfile);
        String cloudEvent = qualificationEventMapper.toQualificationUpdateEvent(data);
        cloudEventPublishingService.sendEvent(data.getPersonId(), cloudEvent,
              cloudEventProperties.getQualification().getSendTopic());
        log.debug("Qualification event sent successfully for personId: {}",
              qualificationProfile.getPersonId());
    }
}
