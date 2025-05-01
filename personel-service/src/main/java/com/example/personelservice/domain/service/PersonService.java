package com.example.personelservice.domain.service;

import com.example.personelservice.domain.model.Person;
import com.example.personelservice.domain.port.PersonEventProvider;
import com.example.personelservice.domain.port.PersonProvider;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonProvider personProvider;
    private final PersonEventProvider personEventProvider;

    public void deletePerson(String id) {
        if (personProvider.getById(id).isEmpty()) {
            log.warn("Person with id {} not found for deletion", id);
        } else {
            personProvider.deleteById(id);
            personEventProvider.sendPersonDeletedEvent(id);
        }
    }

    public Person getById(String id) {
        return personProvider.getById(id).orElseThrow(
              () -> new IllegalArgumentException("Person not found"));
    }

    public void updatePerson(Person person) {
        if (personProvider.getById(person.getId()).isEmpty()) {
            throw new IllegalArgumentException("Person not found");
        }
        Person updatedPerson = personProvider.update(person);
        personEventProvider.sendPersonUpdatedEvent(updatedPerson);
    }

    public Person createPerson(Person person) {
        person.setLastUpdated(new Date().toInstant());
        Person createdPerson = personProvider.create(person);
        personEventProvider.sendPersonUpdatedEvent(createdPerson);
        return createdPerson;
    }
}
