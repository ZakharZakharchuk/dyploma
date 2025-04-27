package com.example.qualificationsvc.provider.persistance.repository;

import com.example.qualificationsvc.provider.persistance.entity.ProjectInfoEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectInfoRepository extends MongoRepository<ProjectInfoEntity, String> {

    List<ProjectInfoEntity> findAllByPersonId(String personId);
}
