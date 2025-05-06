package com.example.qualificationsvc.domain.service;

import com.example.qualificationsvc.domain.model.ProjectInfo;
import com.example.qualificationsvc.domain.model.QualificationProfile;
import com.example.qualificationsvc.domain.model.Skill;
import com.example.qualificationsvc.domain.port.CloudEventPublishingService;
import com.example.qualificationsvc.domain.port.ProjectInfoProvider;
import com.example.qualificationsvc.domain.port.QualificationEventProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QualificationService {

    private final ProjectInfoService projectInfoService;
    private final SkillService skillService;

    public QualificationProfile findById(String id) {
        List<Skill> skills = skillService.getByPersonId(id);
        List<ProjectInfo> projects = projectInfoService.getByPersonId(id);
        return QualificationProfile.builder()
              .personId(id)
              .skills(skills)
              .projects(projects)
              .build();
    }
}
