package com.example.qualificationsvc.domain.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInfo {
    private String id;
    private String personId;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private String description;
}
