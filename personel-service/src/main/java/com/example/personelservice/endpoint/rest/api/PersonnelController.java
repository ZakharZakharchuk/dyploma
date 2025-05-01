package com.example.personelservice.endpoint.rest.api;

import com.example.personelservice.domain.service.PersonService;
import com.example.personelservice.endpoint.mapper.PersonDtoMapper;
import com.example.personelservice.endpoint.rest.dto.CreatePersonRequestDto;
import com.example.personelservice.endpoint.rest.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonnelController implements DefaultApi {

    private final PersonService personService;
    private final PersonDtoMapper personDtoMapper;

    @Override
    public ResponseEntity<Void> personIdDelete(String id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PersonDto> personIdGet(String id) {
        return ResponseEntity.ok(personDtoMapper.toDto(personService.getById(id)));
    }

    @Override
    public ResponseEntity<Void> personPut(PersonDto personDto) {
        personService.updatePerson(personDtoMapper.toDomain(personDto));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> personPost(CreatePersonRequestDto personDto) {
        personService.createPerson(personDtoMapper.toDomain(personDto));
        return ResponseEntity.ok().build();
    }
}
