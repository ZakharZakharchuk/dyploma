package com.example.qualificationsvc.domain.service;

import com.example.qualificationsvc.domain.exception.ObjectNotFoundException;
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
public class SkillService {

    private final SkillProvider skillProvider;
    private final QualificationEventProvider qualificationEventProvider;
    private final ProjectInfoProvider projectInfoProvider;


    public List<Skill> getByPersonId(String personId) {
        return skillProvider.getByPersonId(personId);
    }

    public void addSkill(Skill skill) {
        skillProvider.addSkill(skill);
        sendQualificationEvent(buildQualificationProfile(skill.getPersonId()));
    }

    public void deleteSkill(String id, String personId) {
        if (getByPersonId(personId).stream().noneMatch(skill -> skill.getId().equals(id))) {
            throw new ObjectNotFoundException("No skill with such id found for personId: " + personId);
        }
        skillProvider.deleteSkill(id);
    }

    public void deleteAllByPersonId(String personId) {
        skillProvider.deleteAllByPersonId(personId);
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
