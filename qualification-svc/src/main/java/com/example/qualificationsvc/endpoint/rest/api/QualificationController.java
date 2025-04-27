package com.example.qualificationsvc.endpoint.rest.api;

import com.example.qualificationsvc.domain.service.ProjectInfoService;
import com.example.qualificationsvc.domain.service.QualificationService;
import com.example.qualificationsvc.domain.service.SkillService;
import com.example.qualificationsvc.endpoint.mapper.ProjectInfoDtoMapper;
import com.example.qualificationsvc.endpoint.mapper.QualificationDtoMapper;
import com.example.qualificationsvc.endpoint.mapper.SkillDtoMapper;
import com.example.search.endpoint.rest.api.DefaultApi;
import com.example.search.endpoint.rest.dto.ProjectRequestDto;
import com.example.search.endpoint.rest.dto.QualificationProfileDto;
import com.example.search.endpoint.rest.dto.SkillRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QualificationController implements DefaultApi {

    private final ProjectInfoService projectInfoService;
    private final SkillService skillService;
    private final QualificationService qualificationService;
    private final QualificationDtoMapper qualificationDtoMapper;
    private final SkillDtoMapper skillDtoMapper;
    private final ProjectInfoDtoMapper projectInfoDtoMapper;

    @Override
    public ResponseEntity<QualificationProfileDto> qualificationPersonIdGet(String personId) {
        return ResponseEntity.ok(
              qualificationDtoMapper.toDto(qualificationService.findById(personId)));

    }

    @Override
    public ResponseEntity<Void> qualificationProjectPost(ProjectRequestDto projectRequestDto) {
        projectInfoService.addProject(projectInfoDtoMapper.toDomain(projectRequestDto));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> qualificationProjectProjectIdDelete(String projectId) {
        projectInfoService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> qualificationProjectPut(ProjectRequestDto projectRequestDto) {
        projectInfoService.updateProject(projectInfoDtoMapper.toDomain(projectRequestDto));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> qualificationSkillPost(SkillRequestDto skillRequestDto) {
        skillService.addSkill(skillDtoMapper.toDomain(skillRequestDto));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> qualificationSkillSkillIdDelete(String skillId) {
        skillService.deleteSkill(skillId);
        return ResponseEntity.noContent().build();
    }
}
