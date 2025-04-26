package com.example.search.endpoint.messaging.mapper;

import com.example.search.config.CommonMapperConfig;
import com.example.search.domain.model.PersonProfile;
import com.example.search.endpoint.messaging.dto.PersonUpdatedData;
import com.example.search.endpoint.messaging.dto.QualificationUpdatedData;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = CommonMapperConfig.class)
public interface CloudEventDataMapper {

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "pastProjects", ignore = true)
    @Mapping(target = "certifications", ignore = true)
    PersonProfile toModel(PersonUpdatedData data);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "pastProjects", source = "projects")
    PersonProfile toModel(QualificationUpdatedData data);
}
