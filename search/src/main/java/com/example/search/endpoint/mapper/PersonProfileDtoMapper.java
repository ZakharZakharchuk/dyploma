package com.example.search.endpoint.mapper;

import com.example.search.config.CommonMapperConfig;
import com.example.search.domain.model.PersonProfile;
import com.example.search.endpoint.rest.dto.PersonProfileDto;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class, uses = {ProjectInfoDtoMapper.class})
public interface PersonProfileDtoMapper {

    PersonProfileDto toDto(PersonProfile personProfile);

    PersonProfile toDomain(PersonProfileDto personProfileDto);
}
