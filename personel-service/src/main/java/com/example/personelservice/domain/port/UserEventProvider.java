package com.example.personelservice.domain.port;

import com.example.personelservice.domain.model.Person;

public interface UserEventProvider {
    void sendUserCreated(Person person, String password, String role);
}
