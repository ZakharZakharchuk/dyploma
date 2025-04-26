package com.example.search.domain.service;

import com.example.search.domain.exception.ConsumerNonRetryableException;
import com.example.search.domain.model.PersonProfile;
import com.example.search.domain.model.PersonSearchQuery;
import com.example.search.domain.port.PersonProfileProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonProfileService {

    private final PersonProfileProvider personProfileProvider;

    public PersonProfile getById(String id) {
        return personProfileProvider.getById(id)
              .orElseThrow(() -> new ConsumerNonRetryableException("Person profile not found"));
    }

    public List<PersonProfile> search(PersonSearchQuery query) {
        return personProfileProvider.search(query);
    }

    public void applyPersonUpdate(PersonProfile personProfile) {
        personProfileProvider.save(mergeFullUpdate(personProfile));
    }

    public void applyQuantityUpdate(PersonProfile personProfile) {
        PersonProfile existingProfile = personProfileProvider.getById(personProfile.getPersonId())
              .orElseThrow(() -> new ConsumerNonRetryableException("Person profile not found"));

        existingProfile.setSkills(personProfile.getSkills());
        existingProfile.setPastProjects(personProfile.getPastProjects());

        personProfileProvider.save(existingProfile);
    }

    public void deletePersonProfile(String personId) {
        if (personProfileProvider.getById(personId).isEmpty()) {
            throw new ConsumerNonRetryableException("Person profile not found");
        }
        personProfileProvider.deleteById(personId);
    }

    // ===== Private helper methods =====

    private PersonProfile mergeFullUpdate(PersonProfile incomingProfile) {
        return personProfileProvider.getById(incomingProfile.getPersonId())
              .map(existingProfile -> {
                  existingProfile.setName(incomingProfile.getName());
                  existingProfile.setSurname(incomingProfile.getSurname());
                  existingProfile.setEmail(incomingProfile.getEmail());
                  existingProfile.setPhone(incomingProfile.getPhone());
                  existingProfile.setLocation(incomingProfile.getLocation());
                  existingProfile.setDateOfBirth(incomingProfile.getDateOfBirth());
                  existingProfile.setStartOfServiceDate(incomingProfile.getStartOfServiceDate());
                  existingProfile.setRank(incomingProfile.getRank());
                  existingProfile.setDepartment(incomingProfile.getDepartment());
                  existingProfile.setCurrentPosition(incomingProfile.getCurrentPosition());
                  existingProfile.setCommanderId(incomingProfile.getCommanderId());
                  existingProfile.setLastUpdated(incomingProfile.getLastUpdated());
                  return existingProfile;
              })
              .orElse(incomingProfile);
    }
}
