package com.example.search.domain.model;

import java.util.List;

public record PersonSearchQuery(String name, String surname, String email, String phone,
                                String location, String rank, String department,
                                String currentPosition, String commanderId, List<String> skills,
                                List<String> certifications, String projectName,
                                String projectDescription) {

}
