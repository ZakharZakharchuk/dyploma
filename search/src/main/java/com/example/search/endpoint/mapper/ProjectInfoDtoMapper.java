package com.example.search.endpoint.mapper;

import com.example.search.config.CommonMapperConfig;
import com.example.search.domain.model.ProjectInfo;
import com.example.search.endpoint.rest.dto.ProjectInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface ProjectInfoDtoMapper {

    ProjectInfoDto toDto(ProjectInfo projectInfo);

    ProjectInfo toDomain(ProjectInfoDto projectInfoDto);
}
