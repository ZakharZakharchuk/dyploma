package com.example.candidatelistsvc.endpoint.rest.api;

import com.example.candidatelistsvc.domain.exception.UnauthorizedAccessException;
import com.example.candidatelistsvc.domain.service.AuthorizationService;
import com.example.candidatelistsvc.domain.service.SelectionEntryService;
import com.example.candidatelistsvc.endpoint.rest.dto.AddToSelectionRequestDto;
import com.example.candidatelistsvc.endpoint.rest.dto.HRSelectionEntryDto;
import com.example.candidatelistsvc.endpoint.rest.dto.UpdateSelectionStatusRequestDto;
import com.example.candidatelistsvc.endpoint.rest.mapper.SelectionEntryDtoMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CandidateController implements HrSelectionApi {

    private final SelectionEntryService selectionEntryService;
    private final SelectionEntryDtoMapper selectionEntryDtoMapper;
    private final AuthorizationService authorizationService;

    @Override
    public ResponseEntity<Void> selectionDelete(String id) {
        if (authorizationService.isHR() || authorizationService.isAdmin()) {
            selectionEntryService.deleteSelectionEntry(id,
                  authorizationService.getUserDetails().personId());
            return ResponseEntity.noContent().build();
        }
        throw new UnauthorizedAccessException();
    }

    @Override
    public ResponseEntity<List<HRSelectionEntryDto>> selectionGet() {
        if (authorizationService.isHR() || authorizationService.isAdmin()) {
            return ResponseEntity.ok(
                  selectionEntryService.getSelectionEntriesByHrId(
                              authorizationService.getUserDetails().personId())
                        .stream()
                        .map(selectionEntryDtoMapper::toDto)
                        .toList()
            );
        }
        throw new UnauthorizedAccessException();
    }

    @Override
    public ResponseEntity<Void> selectionPost(AddToSelectionRequestDto
          addToSelectionRequestDto) {
        if (authorizationService.isHR() || authorizationService.isAdmin()) {
            selectionEntryService.createSelectionEntry(
                  selectionEntryDtoMapper.toDomain(
                        addToSelectionRequestDto,
                        authorizationService.getUserDetails().personId()
                  )
            );
            return ResponseEntity.noContent().build();
        }
        throw new UnauthorizedAccessException();
    }

    @Override
    public ResponseEntity<Void> selectionStatusPut(
          UpdateSelectionStatusRequestDto updateSelectionStatusRequestDto) {
        if (authorizationService.isHR() || authorizationService.isAdmin()) {
            selectionEntryService.updateSelectionStatus(
                  updateSelectionStatusRequestDto.getSelectionId(),
                  updateSelectionStatusRequestDto.getStatus().name(),
                  authorizationService.getUserDetails().personId()
            );
            return ResponseEntity.noContent().build();
        }
        throw new UnauthorizedAccessException();
    }
}
