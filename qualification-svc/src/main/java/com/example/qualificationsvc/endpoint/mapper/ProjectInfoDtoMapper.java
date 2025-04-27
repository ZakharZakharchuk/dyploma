package com.example.qualificationsvc.endpoint.mapper;

import com.example.qualificationsvc.config.CommonMapperConfig;
import com.example.qualificationsvc.domain.model.ProjectInfo;
import com.example.search.endpoint.rest.dto.ProjectInfoDto;
import com.example.search.endpoint.rest.dto.ProjectRequestDto;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface ProjectInfoDtoMapper {

    ProjectInfoDto toDto(ProjectInfo domain);
    ProjectInfo toDomain(ProjectRequestDto request);
}
