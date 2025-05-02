package com.example.userservice.domain.service;

import com.example.userservice.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.jwt-secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    @Value("${jwt.refreshExpirationMs}")
    private long refreshExpirationMs;

    public String generateToken(User user) {
        return Jwts.builder()
              .setSubject(user.getEmail())
              .claim("personId", user.getPersonId())
              .claim("role", user.getRole().name())
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
              .signWith(SignatureAlgorithm.HS256, secret)
              .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
              .setSubject(user.getEmail())
              .claim("refresh", true)
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
              .signWith(SignatureAlgorithm.HS256, secret)
              .compact();
    }

    public Claims validate(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
