package com.example.personelservice.provider.messaging.mapper;

import com.example.personelservice.config.CommonMapperConfig;
import com.example.personelservice.domain.model.Person;
import com.example.search.endpoint.messaging.dto.UserCreatedData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface UserEventDataMapper {

    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "email", source = "person.email")
    UserCreatedData toUserCreatedData(Person person, String password, String role);
}
