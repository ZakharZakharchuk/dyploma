package com.example.userservice.provider.persistence.repository;

import com.example.userservice.provider.persistence.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    void deleteByPersonId(String personId);
}
