package com.example.search.endpoint.mapper;

import com.example.search.config.CommonMapperConfig;
import com.example.search.domain.model.PageResult;
import com.example.search.endpoint.rest.dto.PageResultPersonProfileDto;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class, uses = {PersonProfileDtoMapper.class})
public interface PageResultDtoMapper {

    PageResultPersonProfileDto toDto(PageResult pageResult);
}
