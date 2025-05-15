package com.example.qualificationsvc.domain.service;

import com.example.qualificationsvc.domain.model.Role;
import com.example.qualificationsvc.domain.model.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean isAdmin() {
        UserDetails user = getUserDetails();
        return user != null && user.getRole().equals(Role.ADMIN.getRole());
    }

    public boolean isHR() {
        UserDetails user = getUserDetails();
        return user != null && user.getRole().equals(Role.HR.getRole());
    }

    public boolean isManager() {
        UserDetails user = getUserDetails();
        return user != null && user.getRole().equals(Role.MANAGER.getRole());
    }

    public boolean isEligibleUser(String requestedPersonId) {
        UserDetails user = getUserDetails();
        return user != null && user.getRole().equals(Role.USER.getRole())
              && user.getPersonId().equals(requestedPersonId);
    }

    public boolean isUser() {
        UserDetails user = getUserDetails();
        return user != null && user.getRole().equals(Role.USER.getRole());
    }

    private UserDetails getUserDetails() {
        return (UserDetails)
              SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
