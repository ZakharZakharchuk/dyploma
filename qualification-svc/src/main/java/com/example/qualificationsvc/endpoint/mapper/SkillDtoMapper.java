package com.example.qualificationsvc.endpoint.mapper;

import com.example.qualificationsvc.config.CommonMapperConfig;
import com.example.qualificationsvc.domain.model.Skill;
import com.example.search.endpoint.rest.dto.SkillDto;
import com.example.search.endpoint.rest.dto.SkillRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = CommonMapperConfig.class)
public interface SkillDtoMapper {
    SkillDto toDto(Skill domain);
    @Mapping(target = "id", ignore = true)
    Skill toDomain(SkillRequestDto requestDto);
}
