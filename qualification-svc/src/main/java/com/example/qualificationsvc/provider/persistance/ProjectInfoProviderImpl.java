package com.example.qualificationsvc.provider.persistance;

import com.example.qualificationsvc.domain.model.ProjectInfo;
import com.example.qualificationsvc.domain.port.ProjectInfoProvider;
import com.example.qualificationsvc.domain.service.ProjectInfoService;
import com.example.qualificationsvc.provider.persistance.entity.ProjectInfoEntity;
import com.example.qualificationsvc.provider.persistance.mapper.ProjectInfoMapper;
import com.example.qualificationsvc.provider.persistance.repository.ProjectInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectInfoProviderImpl implements ProjectInfoProvider {

    private final ProjectInfoRepository projectInfoRepository;
    private final ProjectInfoMapper projectInfoMapper;

    @Override
    public void addProject(ProjectInfo projectInfo) {
        projectInfoRepository.save(projectInfoMapper.toEntity(projectInfo));
    }

    @Override
    public void updateProject(ProjectInfo projectInfo) {
        projectInfoRepository.findById(projectInfo.getId())
              .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        projectInfoRepository.save(projectInfoMapper.toEntity(projectInfo));
    }

    @Override
    public List<ProjectInfo> getByPersonId(String personId) {
        return projectInfoRepository.findAllByPersonId(personId).stream()
              .map(projectInfoMapper::toDomain)
              .toList();
    }

    @Override
    public void deleteProject(String id) {
        projectInfoRepository.deleteById(id);
    }
}
