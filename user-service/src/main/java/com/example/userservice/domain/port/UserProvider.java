package com.example.userservice.domain.port;

import com.example.userservice.domain.model.User;

public interface UserProvider {

    void createUser(User user);

    User findByEmail(String email);
}
