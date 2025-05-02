package com.example.userservice.endpoint.rest.mapper;

import com.example.personelservice.endpoint.rest.dto.AuthResponseDto;
import com.example.userservice.config.CommonMapperConfig;
import com.example.userservice.domain.model.AuthResponse;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface AuthResponseDtoMapper {

    AuthResponseDto toDto(AuthResponse model);
}
