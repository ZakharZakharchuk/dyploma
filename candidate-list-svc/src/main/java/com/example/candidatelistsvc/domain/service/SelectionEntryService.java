package com.example.candidatelistsvc.domain.service;

import com.example.candidatelistsvc.domain.exception.ObjectNotFoundException;
import com.example.candidatelistsvc.domain.model.SelectionEntry;
import com.example.candidatelistsvc.domain.port.SelectionEntryProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelectionEntryService {

    private final SelectionEntryProvider selectionEntryProvider;

    public List<SelectionEntry> getSelectionEntriesByHrId(String id) {
        return selectionEntryProvider.getSelectionEntriesByHrId(id);
    }

    public SelectionEntry createSelectionEntry(SelectionEntry selectionEntry) {
        return selectionEntryProvider.createSelectionEntry(selectionEntry);
    }

    public void deleteSelectionEntry(String id, String hrId) {
        if (!selectionEntryProvider.getSelectionEntryById(id).getHrId().equals(hrId)) {
            throw new ObjectNotFoundException("No such selection entries found for hrId: " + hrId);
        }
        selectionEntryProvider.deleteSelectionEntry(id);
    }

    public void updateSelectionStatus(String id, String status, String hrId) {
        if (!selectionEntryProvider.getSelectionEntryById(id).getHrId().equals(hrId)) {
            throw new ObjectNotFoundException("No such selection entries found for hrId: " + hrId);
        }
        selectionEntryProvider.updateSelectionStatus(id, status);
    }

    public void deleteByCandidateId(String candidateId) {
        selectionEntryProvider.deleteByCandidateId(candidateId);
    }
}
