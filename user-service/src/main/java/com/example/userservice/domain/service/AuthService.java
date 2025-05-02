package com.example.userservice.domain.service;

import com.example.search.endpoint.messaging.dto.UserCreated;
import com.example.userservice.domain.model.AuthResponse;
import com.example.userservice.domain.model.User;
import com.example.userservice.domain.port.UserProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserProvider userProvider;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public void register(User user) {
        userProvider.createUser(user);
    }

    public AuthResponse login(String email, String rawPassword) {
        User user = userProvider.findByEmail(email);

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new AuthResponse(jwtProvider.generateToken(user),
              jwtProvider.generateRefreshToken(user));
    }

    public AuthResponse refresh(String refreshToken) {
        Claims claims = jwtProvider.validate(refreshToken);
        if (!Boolean.TRUE.equals(claims.get("refresh"))) {
            throw new AccessDeniedException("Invalid refresh token");
        }

        String email = claims.getSubject();
        User user = userProvider.findByEmail(email);
        return new AuthResponse(jwtProvider.generateToken(user),
              jwtProvider.generateRefreshToken(user));
    }
}
