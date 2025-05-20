package com.example.candidatelistsvc.provider.persistance;

import com.example.candidatelistsvc.domain.exception.ObjectNotFoundException;
import com.example.candidatelistsvc.domain.model.SelectionEntry;
import com.example.candidatelistsvc.domain.port.SelectionEntryProvider;
import com.example.candidatelistsvc.provider.persistance.entity.SelectionEntryEntity;
import com.example.candidatelistsvc.provider.persistance.mapper.SelectionEntryEntityMapper;
import com.example.candidatelistsvc.provider.persistance.repository.SelectionEntryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelectionEntryProviderImpl implements SelectionEntryProvider {

    private final SelectionEntryRepository selectionEntryRepository;
    private final SelectionEntryEntityMapper selectionEntryEntityMapper;

    @Override
    public List<SelectionEntry> getSelectionEntriesByHrId(String id) {
        return selectionEntryRepository.findByHrId(id).stream()
              .map(selectionEntryEntityMapper::toDomain)
              .toList();
    }

    @Override
    public SelectionEntry createSelectionEntry(SelectionEntry selectionEntry) {
        return selectionEntryEntityMapper.toDomain(
              selectionEntryRepository.save(selectionEntryEntityMapper.toEntity(selectionEntry))
        );
    }

    @Override
    public void deleteSelectionEntry(String id) {
        selectionEntryRepository.deleteById(id);
    }

    @Override
    public void updateSelectionStatus(String id, String status) {
        SelectionEntryEntity selectionEntryEntity = selectionEntryRepository.findById(id)
              .orElseThrow(() -> new ObjectNotFoundException("Selection entry not found"));
        selectionEntryEntity.setStatus(status);
        selectionEntryRepository.save(selectionEntryEntity);
    }

    @Override
    public SelectionEntry getSelectionEntryById(String id) {
        return selectionEntryRepository.findById(id)
              .map(selectionEntryEntityMapper::toDomain)
              .orElseThrow(() -> new ObjectNotFoundException("Selection entry not found"));
    }

    @Override
    public void deleteByCandidateId(String candidateId) {
        selectionEntryRepository.deleteAllByCandidateId(candidateId);
    }
}
