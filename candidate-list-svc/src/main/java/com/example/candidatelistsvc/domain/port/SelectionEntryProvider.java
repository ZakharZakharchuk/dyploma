package com.example.candidatelistsvc.domain.port;

import com.example.candidatelistsvc.domain.model.SelectionEntry;
import java.util.List;
import java.util.Optional;

public interface SelectionEntryProvider {

    List<SelectionEntry> getSelectionEntriesByHrId(String id);

    SelectionEntry createSelectionEntry(SelectionEntry selectionEntry);


    void deleteSelectionEntry(String id);

    void updateSelectionStatus(String id, String status);

    SelectionEntry getSelectionEntryById(String id);

    void deleteByCandidateId(String candidateId);
}
