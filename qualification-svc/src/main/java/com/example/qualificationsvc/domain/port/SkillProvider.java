package com.example.qualificationsvc.domain.port;

import com.example.qualificationsvc.domain.model.Skill;
import java.util.List;

public interface SkillProvider {

    void addSkill(Skill request);
    List<Skill> getByPersonId(String personId);

    void deleteSkill(String id);

    void deleteAllByPersonId(String personId);
}
