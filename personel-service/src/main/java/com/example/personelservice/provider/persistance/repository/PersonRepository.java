package com.example.personelservice.provider.persistance.repository;

import com.example.personelservice.provider.persistance.entity.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<PersonEntity, String> {

}
