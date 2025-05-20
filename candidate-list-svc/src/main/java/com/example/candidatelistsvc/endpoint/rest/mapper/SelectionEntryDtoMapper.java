package com.example.candidatelistsvc.endpoint.rest.mapper;

import com.example.candidatelistsvc.config.CommonMapperConfig;
import com.example.candidatelistsvc.domain.model.SelectionEntry;
import com.example.candidatelistsvc.endpoint.rest.dto.AddToSelectionRequestDto;
import com.example.candidatelistsvc.endpoint.rest.dto.HRSelectionEntryDto;
import com.example.candidatelistsvc.endpoint.rest.dto.UpdateSelectionStatusRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface SelectionEntryDtoMapper {

    HRSelectionEntryDto toDto(SelectionEntry domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    SelectionEntry toDomain(AddToSelectionRequestDto request, String hrId);

}
