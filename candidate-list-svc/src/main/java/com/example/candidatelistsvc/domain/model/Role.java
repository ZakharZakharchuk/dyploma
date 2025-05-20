package com.example.candidatelistsvc.domain.model;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    HR("HR"),
    MANAGER("MANAGER");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
