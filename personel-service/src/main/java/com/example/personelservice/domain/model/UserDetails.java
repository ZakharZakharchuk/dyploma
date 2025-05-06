package com.example.personelservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {
    private final String email;
    private final String personId;
    private final String role;
}
