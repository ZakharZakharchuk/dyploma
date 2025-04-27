package com.example.qualificationsvc.provider.persistance;

import com.example.qualificationsvc.domain.model.Skill;
import com.example.qualificationsvc.domain.port.SkillProvider;
import com.example.qualificationsvc.provider.persistance.mapper.SkillMapper;
import com.example.qualificationsvc.provider.persistance.repository.SkillRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillProviderImpl implements SkillProvider {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Override
    public void addSkill(Skill skill) {
        skillRepository.save(skillMapper.toEntity(skill));
    }

    @Override
    public List<Skill> getByPersonId(String personId) {
        return skillRepository.findAllByPersonId(personId).stream()
              .map(skillMapper::toDomain)
              .toList();
    }

    @Override
    public void deleteSkill(String id) {
        skillRepository.deleteById(id);
    }
}
