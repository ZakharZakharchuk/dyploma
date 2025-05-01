package com.example.personelservice.domain.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

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
