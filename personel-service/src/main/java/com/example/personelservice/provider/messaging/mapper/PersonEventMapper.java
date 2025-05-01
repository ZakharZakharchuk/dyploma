package com.example.personelservice.provider.messaging.mapper;

import com.example.personelservice.config.CommonMapperConfig;
import com.example.search.endpoint.messaging.dto.PersonDeleted;
import com.example.search.endpoint.messaging.dto.PersonDeletedData;
import com.example.search.endpoint.messaging.dto.PersonUpdated;
import com.example.search.endpoint.messaging.dto.PersonUpdatedData;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Mapper(config = CommonMapperConfig.class)
public class PersonEventMapper {

    @Autowired
    private ObjectMapper cloudEventMapper;

    public String toPersonUpdateEvent(PersonUpdatedData personUpdatedData) {
        PersonUpdated personUpdated = new PersonUpdated();
        personUpdated.setId(UUID.randomUUID().toString());
        personUpdated.setTime(new Date());
        personUpdated.setData(personUpdatedData);
        return toJson(personUpdated);
    }

    public String toPersonDeleteEvent(PersonDeletedData personDeletedData) {
        PersonDeleted personDeleted = new PersonDeleted();
        personDeleted.setId(UUID.randomUUID().toString());
        personDeleted.setTime(new Date());
        personDeleted.setData(personDeletedData);
        return toJson(personDeleted);
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
