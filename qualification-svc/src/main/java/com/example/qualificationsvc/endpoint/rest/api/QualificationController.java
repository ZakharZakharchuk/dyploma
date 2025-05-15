package com.example.qualificationsvc.endpoint.rest.api;

import com.example.qualificationsvc.domain.service.AuthorizationService;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class QualificationController implements DefaultApi {

    private final ProjectInfoService projectInfoService;
    private final SkillService skillService;
    private final QualificationService qualificationService;
    private final QualificationDtoMapper qualificationDtoMapper;
    private final SkillDtoMapper skillDtoMapper;
    private final ProjectInfoDtoMapper projectInfoDtoMapper;
    private final AuthorizationService authorizationService;


    @Override
    public ResponseEntity<QualificationProfileDto> qualificationPersonIdGet(String personId) {
        if (authorizationService.isAdmin() ||
              authorizationService.isHR() ||
              authorizationService.isManager() ||
              authorizationService.isEligibleUser(personId)) {
            return ResponseEntity.ok(
                  qualificationDtoMapper.toDto(qualificationService.findById(personId)));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    public ResponseEntity<Void> qualificationProjectPost(ProjectRequestDto projectRequestDto) {
        if (authorizationService.isAdmin() || authorizationService.isManager()
              || authorizationService.isEligibleUser(projectRequestDto.getPersonId())) {
            projectInfoService.addProject(projectInfoDtoMapper.toDomain(projectRequestDto));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    //TODO resolve delete by user
    @Override
    public ResponseEntity<Void> qualificationProjectProjectIdDelete(String projectId) {
        if (authorizationService.isAdmin() || authorizationService.isManager()
              || authorizationService.isUser()) {
            projectInfoService.deleteProject(projectId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @Override
    public ResponseEntity<Void> qualificationProjectPut(ProjectRequestDto projectRequestDto) {
        if (authorizationService.isAdmin() || authorizationService.isManager()
              || authorizationService.isEligibleUser(projectRequestDto.getPersonId())) {
            projectInfoService.updateProject(projectInfoDtoMapper.toDomain(projectRequestDto));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @Override
    public ResponseEntity<Void> qualificationSkillPost(SkillRequestDto skillRequestDto) {
        if (authorizationService.isAdmin() || authorizationService.isManager()
              || authorizationService.isEligibleUser(skillRequestDto.getPersonId())) {
            skillService.addSkill(skillDtoMapper.toDomain(skillRequestDto));
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    //TODO resolve delete by user
    @Override
    public ResponseEntity<Void> qualificationSkillSkillIdDelete(String skillId) {
        if (authorizationService.isAdmin() || authorizationService.isManager()
              || authorizationService.isUser()) {
            skillService.deleteSkill(skillId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }
}
