package com.example.qualificationsvc.provider.persistance.mapper;

import com.example.qualificationsvc.config.CommonMapperConfig;
import com.example.qualificationsvc.domain.model.Skill;
import com.example.qualificationsvc.provider.persistance.entity.SkillEntity;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface SkillMapper {

    SkillEntity toEntity(Skill domain);

    Skill toDomain(SkillEntity entity);
}
