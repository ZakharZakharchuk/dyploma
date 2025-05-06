package com.example.personelservice.provider.messaging.mapper;

import com.example.personelservice.config.CommonMapperConfig;
import com.example.search.endpoint.messaging.dto.PersonDeleted;
import com.example.search.endpoint.messaging.dto.PersonDeletedData;
import com.example.search.endpoint.messaging.dto.PersonUpdated;
import com.example.search.endpoint.messaging.dto.PersonUpdatedData;
import com.example.search.endpoint.messaging.dto.UserCreated;
import com.example.search.endpoint.messaging.dto.UserCreatedData;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Mapper(config = CommonMapperConfig.class)
public class UserCreatedEventMapper {

    @Autowired
    private ObjectMapper cloudEventMapper;

    public String toUserCreatedEvent(UserCreatedData userCreatedData) {
        UserCreated userCreated = new UserCreated();
        userCreated.setId(UUID.randomUUID().toString());
        userCreated.setTime(new Date());
        userCreated.setData(userCreatedData);
        return toJson(userCreated);
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
