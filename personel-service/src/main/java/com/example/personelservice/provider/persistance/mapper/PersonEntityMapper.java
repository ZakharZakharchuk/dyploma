package com.example.personelservice.provider.persistance.mapper;

import com.example.personelservice.config.CommonMapperConfig;
import com.example.personelservice.domain.model.Person;
import com.example.personelservice.provider.persistance.entity.PersonEntity;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface PersonEntityMapper {

    Person toDomain(PersonEntity personEntity);

    PersonEntity toEntity(Person person);
}
