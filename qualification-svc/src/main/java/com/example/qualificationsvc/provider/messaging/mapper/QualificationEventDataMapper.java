package com.example.qualificationsvc.provider.messaging.mapper;

import com.example.qualificationsvc.config.CommonMapperConfig;
import com.example.qualificationsvc.domain.model.QualificationProfile;
import com.example.qualificationsvc.domain.model.Skill;
import com.example.search.endpoint.messaging.dto.QualificationUpdatedData;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = CommonMapperConfig.class)
public interface QualificationEventDataMapper {

    @Mapping(target = "skills", source = "skills", qualifiedByName = "skillToNameList")
    @Mapping(target = "lastUpdated", ignore = true)
    QualificationUpdatedData toData(QualificationProfile data);

    @Named("skillToNameList")
    static List<String> skillToNameList(List<Skill> skills) {
        if (skills == null) {
            return null;
        }
        return skills.stream()
              .map(Skill::getName)
              .collect(Collectors.toList());
    }

}
