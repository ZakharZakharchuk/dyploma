package com.example.personelservice.domain.port;

import com.example.personelservice.domain.model.Person;

public interface PersonEventProvider {

    void sendPersonUpdatedEvent(Person person);

    void sendPersonDeletedEvent(String id);
}
