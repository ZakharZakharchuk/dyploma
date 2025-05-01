package com.example.personelservice.provider.persistance.entity;

import java.time.Instant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "persons")
public class PersonEntity {

    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String location;
    private Instant dateOfBirth;
    private Instant startOfServiceDate;
    private String rank;
    private String department;
    private String currentPosition;
    private String commanderId;
    private Instant lastUpdated;

}
