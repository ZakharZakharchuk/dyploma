package com.example.search.provider.persistance.mapper;

import com.example.search.config.CommonMapperConfig;
import com.example.search.domain.model.ProjectInfo;
import com.example.search.provider.persistance.entity.ProjectInfoEntity;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface ProjectInfoEntityMapper {

    ProjectInfoEntity toEntity(ProjectInfo domain);

    ProjectInfo toDomain(ProjectInfoEntity entity);
}
