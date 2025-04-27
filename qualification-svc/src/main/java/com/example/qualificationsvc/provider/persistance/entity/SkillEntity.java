package com.example.qualificationsvc.provider.persistance.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "skills")
public class SkillEntity {

    @Id
    private String id;
    private String personId;
    private String name;
}
