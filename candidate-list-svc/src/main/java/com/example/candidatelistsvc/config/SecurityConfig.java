package com.example.candidatelistsvc.config;

import com.example.candidatelistsvc.domain.service.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
              .cors(Customizer.withDefaults()) // ✅ Enable CORS
              .csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                          "/v3/api-docs/**",
                          "/swagger-ui/**",
                          "/swagger-ui.html"
                    ).permitAll()
                    .anyRequest().authenticated()
              )
              .sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
              .httpBasic(AbstractHttpConfigurer::disable)
              .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}

