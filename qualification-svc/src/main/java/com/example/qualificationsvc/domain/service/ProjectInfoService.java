package com.example.qualificationsvc.domain.service;

import com.example.qualificationsvc.domain.model.ProjectInfo;
import com.example.qualificationsvc.domain.port.ProjectInfoProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectInfoService {

    private final ProjectInfoProvider projectInfoProvider;

    public void addProject(ProjectInfo request) {
        projectInfoProvider.addProject(request);
    }

    public void deleteProject(String id) {
        projectInfoProvider.deleteProject(id);
    }

    public List<ProjectInfo> getByPersonId(String personId) {
        return projectInfoProvider.getByPersonId(personId);
    }

    public void updateProject(ProjectInfo request) {
        projectInfoProvider.updateProject(request);
    }
}
