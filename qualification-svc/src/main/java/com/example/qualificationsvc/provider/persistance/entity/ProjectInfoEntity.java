package com.example.qualificationsvc.provider.persistance.entity;

import java.time.Instant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "projects")
public class ProjectInfoEntity {

    @Id
    private String id;
    private String personId;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private String description;
}
