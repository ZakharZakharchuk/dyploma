package com.example.qualificationsvc.endpoint.mapper;

import com.example.qualificationsvc.config.CommonMapperConfig;
import com.example.qualificationsvc.domain.model.QualificationProfile;
import com.example.search.endpoint.rest.dto.QualificationProfileDto;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class, uses = {ProjectInfoDtoMapper.class,
      SkillDtoMapper.class})
public interface QualificationDtoMapper {

    QualificationProfileDto toDto(QualificationProfile domain);
}
