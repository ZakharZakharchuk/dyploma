package com.example.search.endpoint.rest.api;

import com.example.search.domain.service.AuthorizationService;
import com.example.search.domain.service.PersonProfileService;
import com.example.search.endpoint.mapper.PageResultDtoMapper;
import com.example.search.endpoint.mapper.PersonProfileDtoMapper;
import com.example.search.endpoint.mapper.PersonSearchCriteriaDtoMapper;
import com.example.search.endpoint.rest.dto.PageResultPersonProfileDto;
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
    private final PageResultDtoMapper pageResultDtoMapper;

    @Override
    public ResponseEntity<PageResultPersonProfileDto> apiSearchPersonsSearchPost(
          PersonSearchCriteriaDto personSearchCriteriaDto) {
        if (authorizationService.isAdmin() ||
              authorizationService.isManager() ||
              authorizationService.isHR()) {
            PageResultPersonProfileDto result = pageResultDtoMapper.toDto(
                  personProfileService.search(
                        personSearchCriteriaDtoMapper.toDomain(personSearchCriteriaDto)));
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }
}
