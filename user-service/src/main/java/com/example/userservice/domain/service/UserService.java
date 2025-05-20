package com.example.userservice.domain.service;

import com.example.userservice.domain.port.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProvider userProvider;
    public void deleteUser(String id) {
        userProvider.deleteUser(id);
    }
}
