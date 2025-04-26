package com.example.search.provider.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "person_profiles")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonProfileEntity {

    @Id
    private String personId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String location;
    @Field(type = FieldType.Date)
    private Instant dateOfBirth;
    @Field(type = FieldType.Date)
    private Instant startOfServiceDate;
    private String department;
    private String rank;
    private String currentPosition;
    private String commanderId;
    @Field(type = FieldType.Date)
    private Instant lastUpdated;
    private List<String> skills;
    private List<ProjectInfoEntity> pastProjects;
    private List<String> certifications;
}
