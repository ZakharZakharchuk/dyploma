package com.example.qualificationsvc.provider.persistance.mapper;

import com.example.qualificationsvc.config.CommonMapperConfig;
import com.example.qualificationsvc.domain.model.ProjectInfo;
import com.example.qualificationsvc.provider.persistance.entity.ProjectInfoEntity;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface ProjectInfoMapper {

    ProjectInfoEntity toEntity(ProjectInfo domain);

    ProjectInfo toDomain(ProjectInfoEntity entity);
}
