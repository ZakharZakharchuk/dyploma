package com.example.personelservice.domain.port;

import com.example.personelservice.domain.model.Person;
import java.util.Optional;

public interface PersonProvider {

    Optional<Person> getById(String id);

    Person create(Person person);

    Person update(Person person);

    void deleteById(String id);
}
