package com.example.userservice.provider.persistence.mapper;

import com.example.userservice.config.CommonMapperConfig;
import com.example.userservice.domain.model.User;
import com.example.userservice.provider.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface UserEntityMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User model);
}
