package com.example.qualificationsvc.domain.service;

import com.example.qualificationsvc.domain.model.ProjectInfo;
import com.example.qualificationsvc.domain.model.QualificationProfile;
import com.example.qualificationsvc.domain.model.Skill;
import com.example.qualificationsvc.domain.port.ProjectInfoProvider;
import com.example.qualificationsvc.domain.port.QualificationEventProvider;
import com.example.qualificationsvc.domain.port.SkillProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectInfoService {

    private final ProjectInfoProvider projectInfoProvider;
    private final SkillProvider skillProvider;
    private final QualificationEventProvider qualificationEventProvider;

    public void addProject(ProjectInfo request) {
        projectInfoProvider.addProject(request);
        sendQualificationEvent(buildQualificationProfile(request.getPersonId()));
    }

    public void deleteProject(String id) {
        projectInfoProvider.deleteProject(id);
    }

    public List<ProjectInfo> getByPersonId(String personId) {
        return projectInfoProvider.getByPersonId(personId);
    }

    public void updateProject(ProjectInfo request) {
        projectInfoProvider.updateProject(request);
        sendQualificationEvent(buildQualificationProfile(request.getPersonId()));
    }

    public void deleteAllByPersonId(String personId) {
        projectInfoProvider.deleteAllByPersonId(personId);
        sendQualificationEvent(buildQualificationProfile(personId));
    }

    public void sendQualificationEvent(QualificationProfile qualificationProfile) {
        qualificationEventProvider.sendQualificationEvent(qualificationProfile);
    }

    private QualificationProfile buildQualificationProfile(String personId) {
        List<ProjectInfo> projects = projectInfoProvider.getByPersonId(personId);
        List<Skill> skills = skillProvider.getByPersonId(personId);
        return QualificationProfile.builder()
              .personId(personId)
              .projects(projects)
              .skills(skills)
              .build();
    }
}
