package com.example.candidatelistsvc.domain.service;

import com.example.candidatelistsvc.domain.model.Role;
import com.example.candidatelistsvc.domain.model.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean isHR() {
        UserDetails user = getUserDetails();
        return user != null && user.role().equals(Role.HR.getRole());
    }

    public boolean isAdmin() {
        UserDetails user = getUserDetails();
        return user != null && user.role().equals(Role.ADMIN.getRole());
    }

    public UserDetails getUserDetails() {
        return (UserDetails)
              SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
