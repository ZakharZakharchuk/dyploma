package com.example.search.provider.persistance.mapper;

import com.example.search.config.CommonMapperConfig;
import com.example.search.domain.model.PersonProfile;
import com.example.search.provider.persistance.entity.PersonProfileEntity;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class, uses = {ProjectInfoEntityMapper.class})
public interface PersonProfileEntityMapper {

    PersonProfileEntity toEntity(PersonProfile domain);

    PersonProfile toDomain(PersonProfileEntity entity);
}
