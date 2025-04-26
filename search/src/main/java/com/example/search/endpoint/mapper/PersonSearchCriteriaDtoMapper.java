package com.example.search.endpoint.mapper;

import com.example.search.config.CommonMapperConfig;
import com.example.search.domain.model.PersonSearchQuery;
import com.example.search.endpoint.rest.dto.PersonSearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface PersonSearchCriteriaDtoMapper {

    PersonSearchQuery toDomain(PersonSearchCriteriaDto dto);
}
