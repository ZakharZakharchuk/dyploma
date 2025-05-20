package com.example.candidatelistsvc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectionEntry {

    private String id;
    private String hrId;
    private String candidateId;
    private String note;
    private String status;
}
