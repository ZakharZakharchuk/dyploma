package com.example.search.provider.persistance;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.search.domain.model.PageResult;
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

    public PageResult search(PersonSearchQuery criteria) {
        List<Query> mustQueries = new ArrayList<>();
        List<Query> filterQueries = new ArrayList<>();
        List<Query> shouldQueries = new ArrayList<>();

        // 🔥 High Relevance
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

        // ✅ Medium Relevance (як skills, точний пошук по keyword)
        if (criteria.projectName() != null || criteria.projectDescription() != null) {
            List<Query> nestedMust = new ArrayList<>();

            if (criteria.projectName() != null) {
                nestedMust.add(Query.of(q -> q.term(
                      t -> t.field("pastProjects.name.keyword").value(criteria.projectName())
                )));
            }

            if (criteria.projectDescription() != null) {
                nestedMust.add(Query.of(q -> q.term(
                      t -> t.field("pastProjects.description.keyword").value(criteria.projectDescription())
                )));
            }

            if (!nestedMust.isEmpty()) {
                mustQueries.add(Query.of(q -> q.nested(n -> n
                      .path("pastProjects")
                      .query(Query.of(b -> b.bool(bb -> bb.must(nestedMust))))
                )));
            }
        }


        if (criteria.location() != null) {
            filterQueries.add(Query.of(
                  q -> q.term(t -> t.field("location.keyword").value(criteria.location()))));
        }

        // ✅ Low Relevance
        if (criteria.name() != null) {
            shouldQueries.add(Query.of(q -> q.match(m -> m.field("name").query(criteria.name()))));
        }
        if (criteria.surname() != null) {
            shouldQueries.add(
                  Query.of(q -> q.match(m -> m.field("surname").query(criteria.surname()))));
        }

        // ❌ Filters only
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

        // Final bool query
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
            int from = criteria.page() * criteria.size();

            SearchRequest request = SearchRequest.of(s -> s
                  .index("person_profiles")
                  .query(finalQuery)
                  .from(from)
                  .size(criteria.size())
            );

            SearchResponse<PersonProfileEntity> response = elasticsearchClient.search(
                  request,
                  PersonProfileEntity.class
            );

            List<PersonProfile> items = response.hits().hits().stream()
                  .map(hit -> personProfileEntityMapper.toDomain(hit.source()))
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());

            long totalHits =
                  response.hits().total() != null ? response.hits().total().value() : items.size();

            return new PageResult(items, totalHits, criteria.page(), criteria.size());

        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch query failed", e);
        }
    }

}
