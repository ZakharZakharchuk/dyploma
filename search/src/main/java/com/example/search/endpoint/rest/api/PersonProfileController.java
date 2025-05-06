package com.example.search.endpoint.rest.api;

import com.example.search.domain.service.AuthorizationService;
import com.example.search.domain.service.PersonProfileService;
import com.example.search.endpoint.mapper.PersonProfileDtoMapper;
import com.example.search.endpoint.mapper.PersonSearchCriteriaDtoMapper;
import com.example.search.endpoint.rest.dto.PersonProfileDto;
import com.example.search.endpoint.rest.dto.PersonSearchCriteriaDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PersonProfileController implements SearchApi {

    private final PersonProfileService personProfileService;
    private final PersonProfileDtoMapper personProfileDtoMapper;
    private final PersonSearchCriteriaDtoMapper personSearchCriteriaDtoMapper;
    private final AuthorizationService authorizationService;

    @Override
    public ResponseEntity<List<PersonProfileDto>> apiSearchPersonsSearchPost(
          PersonSearchCriteriaDto personSearchCriteriaDto) {
        if (authorizationService.isAdmin() ||
              authorizationService.isHR()) {
            List<PersonProfileDto> result = personProfileService.search(
                        personSearchCriteriaDtoMapper.toDomain(personSearchCriteriaDto)).stream()
                  .map(personProfileDtoMapper::toDto)
                  .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }
}
