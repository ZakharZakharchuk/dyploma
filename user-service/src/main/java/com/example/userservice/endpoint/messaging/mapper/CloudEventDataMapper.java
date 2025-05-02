package com.example.userservice.endpoint.messaging.mapper;

import com.example.search.endpoint.messaging.dto.UserCreatedData;
import com.example.userservice.config.CommonMapperConfig;
import com.example.userservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface CloudEventDataMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    User toModel(UserCreatedData data);
}
