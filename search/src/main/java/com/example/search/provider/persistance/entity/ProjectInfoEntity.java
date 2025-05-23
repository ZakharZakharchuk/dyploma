package com.example.search.provider.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInfoEntity {

    private String name;
    private String description;
    private String startDate;
    private String endDate;
}
