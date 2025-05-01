package com.example.personelservice.domain.mapper;

import com.example.personelservice.config.CommonMapperConfig;
import com.example.personelservice.domain.model.Person;
import com.example.personelservice.endpoint.rest.dto.PersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface PersonMapper {

    Person toPerson(PersonDto personRequestDtoon);

    PersonDto toPersonDto(Person person);
}
