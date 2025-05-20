package com.example.personelservice.domain.service;

import com.example.personelservice.domain.model.Role;
import com.example.personelservice.domain.model.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean isAdmin() {
        UserDetails user = getUserDetails();
        return user != null && user.role().equals(Role.ADMIN.getRole());
    }

    public boolean isHR() {
        UserDetails user = getUserDetails();
        return user != null && user.role().equals(Role.HR.getRole());
    }

    public boolean isManager() {
        UserDetails user = getUserDetails();
        return user != null && user.role().equals(Role.MANAGER.getRole());
    }

    public boolean isEligibleUser(String requestedPersonId) {
        UserDetails user = getUserDetails();
        return user != null && user.role().equals(Role.USER.getRole())
              && user.personId().equals(requestedPersonId);
    }

    private UserDetails getUserDetails() {
        return (UserDetails)
              SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
