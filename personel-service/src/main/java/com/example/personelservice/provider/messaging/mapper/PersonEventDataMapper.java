package com.example.personelservice.provider.messaging.mapper;

import com.example.personelservice.config.CommonMapperConfig;
import com.example.personelservice.domain.model.Person;
import com.example.search.endpoint.messaging.dto.PersonDeletedData;
import com.example.search.endpoint.messaging.dto.PersonUpdatedData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface PersonEventDataMapper {

    @Mapping(target = "personId", source = "data.id")
    PersonUpdatedData toUpdatedData(Person data);
    PersonDeletedData toDeletedData(String personId);
}
