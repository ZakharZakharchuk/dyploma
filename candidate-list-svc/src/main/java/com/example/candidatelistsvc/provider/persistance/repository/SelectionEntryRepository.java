package com.example.candidatelistsvc.provider.persistance.repository;

import com.example.candidatelistsvc.provider.persistance.entity.SelectionEntryEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionEntryRepository extends MongoRepository<SelectionEntryEntity, String> {

    List<SelectionEntryEntity> findByHrId(String hrId);

    void deleteAllByCandidateId(String candidateId);
}
