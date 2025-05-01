package com.example.search.provider.persistance;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.search.domain.model.PersonProfile;
import com.example.search.domain.model.PersonSearchQuery;
import com.example.search.domain.port.PersonProfileProvider;
import com.example.search.provider.persistance.entity.PersonProfileEntity;
import com.example.search.provider.persistance.mapper.PersonProfileEntityMapper;
import com.example.search.provider.persistance.repository.PersonProfileRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonProfileProviderImpl implements PersonProfileProvider {

    private final PersonProfileRepository personProfileRepository;
    private final ElasticsearchClient elasticsearchClient;
    private final PersonProfileEntityMapper personProfileEntityMapper;

    @Override
    public Optional<PersonProfile> getById(String id) {
        PersonProfileEntity entity = personProfileRepository.findById(id)
              .orElse(null);
        PersonProfile personProfile = personProfileEntityMapper.toDomain(entity);
        return personProfileRepository.findById(id)
              .map(personProfileEntityMapper::toDomain);
    }

    public void save(PersonProfile personProfile) {
        PersonProfileEntity entity = personProfileEntityMapper.toEntity(personProfile);
        personProfileRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        personProfileRepository.deleteById(id);
    }

    public List<PersonProfile> search(PersonSearchQuery criteria) {
        List<Query> mustQueries = new ArrayList<>(); // For scored relevance
        List<Query> filterQueries = new ArrayList<>(); // For non-scored filters
        List<Query> shouldQueries = new ArrayList<>(); // For boosting optional matches

        // 🔥 High Relevance (must + boost)
        if (criteria.rank() != null) {
            mustQueries.add(
                  Query.of(q -> q.match(m -> m.field("rank").query(criteria.rank()).boost(2.0f))));
        }

        if (criteria.skills() != null && !criteria.skills().isEmpty()) {
            mustQueries.add(Query.of(q -> q.terms(t -> t
                  .field("skills.keyword")
                  .terms(v -> v.value(criteria.skills().stream().map(FieldValue::of).toList()))
            )));
        }

        // ✅ Medium relevance (should)
        if (criteria.projectName() != null || criteria.projectDescription() != null) {
            List<Query> nestedShoulds = new ArrayList<>();

            if (criteria.projectName() != null) {
                nestedShoulds.add(Query.of(q -> q.match(
                      m -> m.field("pastProjects.name").query(criteria.projectName()))));
            }
            if (criteria.projectDescription() != null) {
                nestedShoulds.add(Query.of(q -> q.match(m -> m.field("pastProjects.description")
                      .query(criteria.projectDescription()))));
            }

            if (!nestedShoulds.isEmpty()) {
                shouldQueries.add(Query.of(q -> q.nested(n -> n
                      .path("pastProjects")
                      .query(Query.of(b -> b.bool(bb -> bb.should(nestedShoulds))))
                )));
            }
        }

        if (criteria.location() != null) {
            filterQueries.add(Query.of(
                  q -> q.term(t -> t.field("location.keyword").value(criteria.location()))));
        }

        // ✅ Low Relevance — used as optional match
        if (criteria.name() != null) {
            shouldQueries.add(Query.of(q -> q.match(m -> m.field("name").query(criteria.name()))));
        }

        if (criteria.surname() != null) {
            shouldQueries.add(
                  Query.of(q -> q.match(m -> m.field("surname").query(criteria.surname()))));
        }

        // ❌ No relevance — only filter
        if (criteria.email() != null) {
            filterQueries.add(
                  Query.of(q -> q.term(t -> t.field("email.keyword").value(criteria.email()))));
        }

        if (criteria.phone() != null) {
            filterQueries.add(
                  Query.of(q -> q.term(t -> t.field("phone.keyword").value(criteria.phone()))));
        }

        if (criteria.commanderId() != null) {
            filterQueries.add(Query.of(
                  q -> q.term(t -> t.field("commanderId.keyword").value(criteria.commanderId()))));
        }

        if (criteria.department() != null) {
            filterQueries.add(Query.of(
                  q -> q.term(t -> t.field("department.keyword").value(criteria.department()))));
        }

        if (criteria.currentPosition() != null) {
            filterQueries.add(Query.of(q -> q.term(
                  t -> t.field("currentPosition.keyword").value(criteria.currentPosition()))));
        }

        if (criteria.certifications() != null && !criteria.certifications().isEmpty()) {
            filterQueries.add(Query.of(q -> q.terms(t -> t
                  .field("certifications.keyword")
                  .terms(v -> v.value(
                        criteria.certifications().stream().map(FieldValue::of).toList()))
            )));
        }

        // Assemble full query
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
        if (!mustQueries.isEmpty()) {
            boolQueryBuilder.must(mustQueries);
        }
        if (!shouldQueries.isEmpty()) {
            boolQueryBuilder.should(shouldQueries);
        }
        if (!filterQueries.isEmpty()) {
            boolQueryBuilder.filter(filterQueries);
        }

        Query finalQuery = Query.of(q -> q.bool(boolQueryBuilder.build()));

        try {
            SearchRequest request = SearchRequest.of(s -> s
                  .index("person_profiles")
                  .query(finalQuery)
            );

            SearchResponse<PersonProfileEntity> response = elasticsearchClient.search(request,
                  PersonProfileEntity.class);

            return response.hits().hits().stream()
                  .map(hit -> personProfileEntityMapper.toDomain(hit.source()))
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch query failed", e);
        }
    }
}
