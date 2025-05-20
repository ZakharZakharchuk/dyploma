package com.example.candidatelistsvc.config;

import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
      componentModel = "spring",
      collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
      nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
      injectionStrategy = InjectionStrategy.CONSTRUCTOR,
      unmappedTargetPolicy = ReportingPolicy.ERROR,
      builder = @Builder
)
public interface CommonMapperConfig {

}
