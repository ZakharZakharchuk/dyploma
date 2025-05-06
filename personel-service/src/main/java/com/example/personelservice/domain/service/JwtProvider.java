package com.example.personelservice.domain.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.jwt-secret}")
    private String secret;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims validate(String token) {
        return Jwts.parserBuilder()
              .setSigningKey(signingKey)
              .build()
              .parseClaimsJws(token)
              .getBody();
    }
}
