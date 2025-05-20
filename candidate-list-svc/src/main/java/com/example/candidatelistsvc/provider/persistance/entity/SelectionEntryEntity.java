package com.example.candidatelistsvc.provider.persistance.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "selection_entries")
public class SelectionEntryEntity {

    @Id
    private String id;
    private String hrId;
    private String candidateId;
    private String note;
    private String status;
}
