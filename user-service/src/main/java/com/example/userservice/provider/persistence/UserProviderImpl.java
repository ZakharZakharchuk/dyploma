package com.example.userservice.provider.persistence;

import com.example.userservice.domain.model.User;
import com.example.userservice.domain.port.UserProvider;
import com.example.userservice.provider.persistence.mapper.UserEntityMapper;
import com.example.userservice.provider.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProviderImpl implements UserProvider {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void createUser(User user) {
        userRepository.insert(userEntityMapper.toEntity(user));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
              .map(userEntityMapper::toDomain)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
