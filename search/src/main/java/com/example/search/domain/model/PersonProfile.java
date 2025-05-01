package com.example.search.domain.model;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonProfile {

    private String personId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String location;
    private Date dateOfBirth;
    private Date startOfServiceDate;
    private String rank;
    private String department;
    private String currentPosition;
    private String commanderId;
    private Date lastUpdated;
    private List<String> skills;
    private List<ProjectInfo> pastProjects;
    private List<String> certifications;

}
