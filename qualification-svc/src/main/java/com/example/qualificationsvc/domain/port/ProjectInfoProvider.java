package com.example.qualificationsvc.domain.port;

import com.example.qualificationsvc.domain.model.ProjectInfo;
import java.util.List;

public interface ProjectInfoProvider {

    void addProject(ProjectInfo projectInfo);

    void updateProject(ProjectInfo projectInfo);

    List<ProjectInfo> getByPersonId(String personId);

    void deleteProject(String id);

}
