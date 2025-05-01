package com.example.search.endpoint.rest.api;

import com.example.search.domain.service.PersonProfileService;
import com.example.search.endpoint.mapper.PersonProfileDtoMapper;
import com.example.search.endpoint.mapper.PersonSearchCriteriaDtoMapper;
import com.example.search.endpoint.rest.dto.PersonProfileDto;
import com.example.search.endpoint.rest.dto.PersonSearchCriteriaDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PersonProfileController implements SearchApi {

    private final PersonProfileService personProfileService;
    private final PersonProfileDtoMapper personProfileDtoMapper;
    private final PersonSearchCriteriaDtoMapper personSearchCriteriaDtoMapper;

    @Override
    public ResponseEntity<List<PersonProfileDto>> apiSearchPersonsSearchPost(
          PersonSearchCriteriaDto personSearchCriteriaDto) {
        List<PersonProfileDto> result = personProfileService.search(
                    personSearchCriteriaDtoMapper.toDomain(personSearchCriteriaDto)).stream()
              .map(personProfileDtoMapper::toDto)
              .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
