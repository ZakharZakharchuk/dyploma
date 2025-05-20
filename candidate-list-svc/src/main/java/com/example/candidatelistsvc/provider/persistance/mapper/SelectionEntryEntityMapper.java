package com.example.candidatelistsvc.provider.persistance.mapper;

import com.example.candidatelistsvc.config.CommonMapperConfig;
import com.example.candidatelistsvc.domain.model.SelectionEntry;
import com.example.candidatelistsvc.provider.persistance.entity.SelectionEntryEntity;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface SelectionEntryEntityMapper {

    SelectionEntry toDomain(SelectionEntryEntity selectionEntryEntity);

    SelectionEntryEntity toEntity(SelectionEntry selectionEntry);
}
