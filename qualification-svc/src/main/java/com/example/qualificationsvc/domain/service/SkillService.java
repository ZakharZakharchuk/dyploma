package com.example.qualificationsvc.domain.service;

import com.example.qualificationsvc.domain.model.Skill;
import com.example.qualificationsvc.domain.port.SkillProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillProvider skillProvider;

    public List<Skill> getByPersonId(String personId) {
        return skillProvider.getByPersonId(personId);
    }
    public void addSkill(Skill skill) {
        skillProvider.addSkill(skill);
    }
    public void deleteSkill(String id) {
        skillProvider.deleteSkill(id);
    }
}
