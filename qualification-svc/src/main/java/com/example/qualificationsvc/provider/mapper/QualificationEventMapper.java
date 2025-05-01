package com.example.qualificationsvc.provider.mapper;

import com.example.qualificationsvc.config.CommonMapperConfig;
import com.example.search.endpoint.messaging.dto.QualificationUpdated;
import com.example.search.endpoint.messaging.dto.QualificationUpdatedData;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Mapper(config = CommonMapperConfig.class)
public abstract class QualificationEventMapper {

    @Autowired
    private ObjectMapper cloudEventMapper;

    public String toQualificationUpdateEvent(QualificationUpdatedData qualificationUpdatedData) {
        QualificationUpdated qualificationUpdated = new QualificationUpdated();
        qualificationUpdated.setId(UUID.randomUUID().toString());
        qualificationUpdated.setTime(new Date());
        qualificationUpdated.setData(qualificationUpdatedData);
        return toJson(qualificationUpdated);
    }

    private String toJson(Object event) {
        try {
            return cloudEventMapper.writeValueAsString(event);
        } catch (Exception e) {
            log.error("Error converting event to JSON", e);
            throw new RuntimeException("Error converting event to JSON", e);
        }
    }
}
