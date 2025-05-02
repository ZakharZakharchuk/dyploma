package com.example.userservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String personId;
    private String email;
    private String passwordHash;
    private Role role;

    public enum Role {
        USER, ADMIN, HR
    }
}
