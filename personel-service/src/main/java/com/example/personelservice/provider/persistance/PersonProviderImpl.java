package com.example.personelservice.provider.persistance;

import com.example.personelservice.domain.model.Person;
import com.example.personelservice.domain.port.PersonProvider;
import com.example.personelservice.provider.persistance.mapper.PersonEntityMapper;
import com.example.personelservice.provider.persistance.repository.PersonRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonProviderImpl implements PersonProvider {

    private final PersonRepository personRepository;
    private final PersonEntityMapper personEntityMapper;

    public void deleteById(String id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> getById(String id) {
        return personRepository.findById(id).map(personEntityMapper::toDomain);
    }

    public Person create(Person person) {
        return personEntityMapper.toDomain(
              personRepository.insert(personEntityMapper.toEntity(person)));
    }

    public Person update(Person person) {
        return personEntityMapper.toDomain(personRepository.save(personEntityMapper.toEntity(person)));
    }
}
