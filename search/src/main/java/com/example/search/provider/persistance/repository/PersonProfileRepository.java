package com.example.search.provider.persistance.repository;

import com.example.search.provider.persistance.entity.PersonProfileEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonProfileRepository extends
      ElasticsearchRepository<PersonProfileEntity, String> {

}
