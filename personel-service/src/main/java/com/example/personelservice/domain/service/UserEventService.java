package com.example.personelservice.domain.service;

import com.example.personelservice.domain.model.Person;
import com.example.personelservice.domain.port.UserEventProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventService {

    private final UserEventProvider userEventProvider;
    private final PasswordEncoder passwordEncoder;

    public void sendUserCreated(Person person, String password, String role) {
        userEventProvider.sendUserCreated(person, passwordEncoder.encode(password), role);
    }
}
