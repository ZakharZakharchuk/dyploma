package com.example.search.domain.port;

import com.example.search.domain.model.PersonProfile;
import com.example.search.domain.model.PersonSearchQuery;
import java.util.List;
import java.util.Optional;

public interface PersonProfileProvider {

    Optional<PersonProfile> getById(String id);

    void save(PersonProfile personProfile);

    void deleteById(String id);

    List<PersonProfile> search(PersonSearchQuery query);
}
