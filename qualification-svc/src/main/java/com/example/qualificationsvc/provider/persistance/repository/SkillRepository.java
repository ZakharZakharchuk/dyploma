package com.example.qualificationsvc.provider.persistance.repository;

import com.example.qualificationsvc.provider.persistance.entity.SkillEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkillRepository extends MongoRepository<SkillEntity, String> {

    List<SkillEntity> findAllByPersonId(String personId);

    void deleteAllByPersonId(String personId);
}
