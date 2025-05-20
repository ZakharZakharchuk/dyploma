package com.example.personelservice.endpoint.rest.api;

import com.example.personelservice.domain.exception.UnauthorizedAccessException;
import com.example.personelservice.domain.model.Person;
import com.example.personelservice.domain.service.AuthorizationService;
import com.example.personelservice.domain.service.PersonService;
import com.example.personelservice.domain.service.UserEventService;
import com.example.personelservice.endpoint.mapper.PersonDtoMapper;
import com.example.personelservice.endpoint.rest.dto.CreatePersonRequestDto;
import com.example.personelservice.endpoint.rest.dto.PersonDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PersonnelController implements DefaultApi {

    private final PersonService personService;
    private final PersonDtoMapper personDtoMapper;
    private final UserEventService userEventService;
    private final AuthorizationService authorizationService;

    @Override
    public ResponseEntity<Void> personIdDelete(String id) {
        if (authorizationService.isAdmin() || authorizationService.isManager()) {
            personService.deletePerson(id);
            return ResponseEntity.noContent().build();
        }
        throw new UnauthorizedAccessException();
    }

    @Override
    public ResponseEntity<PersonDto> personIdGet(String id) {
        if (authorizationService.isAdmin() ||
              authorizationService.isHR() ||
              authorizationService.isManager() ||
              authorizationService.isEligibleUser(id)) {
            return ResponseEntity.ok(personDtoMapper.toDto(personService.getById(id)));
        }
        throw new UnauthorizedAccessException();
    }

    @Override
    public ResponseEntity<Void> personPut(PersonDto personDto) {
        if (authorizationService.isAdmin() || authorizationService.isManager()) {
            personService.updatePerson(personDtoMapper.toDomain(personDto));
            return ResponseEntity.noContent().build();
        }
        throw new UnauthorizedAccessException();
    }

    @Override
    public ResponseEntity<Void> personPost(CreatePersonRequestDto personDto) {
        if (authorizationService.isAdmin() || authorizationService.isManager()) {
            Person createdPerson = personService.createPerson(personDtoMapper.toDomain(personDto));
            userEventService.sendUserCreated(
                  createdPerson,
                  personDto.getPassword(),
                  personDto.getRole().toString());
            return ResponseEntity.ok().build();
        }
        throw new UnauthorizedAccessException();
    }
}
