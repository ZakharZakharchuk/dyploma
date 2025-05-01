package com.example.personelservice.endpoint.mapper;

import com.example.personelservice.config.CommonMapperConfig;
import com.example.personelservice.domain.model.Person;
import com.example.personelservice.endpoint.rest.dto.CreatePersonRequestDto;
import com.example.personelservice.endpoint.rest.dto.PersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface PersonDtoMapper {

    PersonDto toDto(Person domain);

    Person toDomain(PersonDto request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    Person toDomain(CreatePersonRequestDto request);
}
