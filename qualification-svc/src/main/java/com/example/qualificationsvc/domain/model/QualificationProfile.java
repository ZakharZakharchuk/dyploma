package com.example.qualificationsvc.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QualificationProfile {

    private String personId;
    private List<Skill> skills;
    private List<ProjectInfo> projects;
}
