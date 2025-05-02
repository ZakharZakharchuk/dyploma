package com.example.userservice.endpoint.rest.api;

import com.example.personelservice.endpoint.rest.api.DefaultApi;
import com.example.personelservice.endpoint.rest.dto.AuthResponseDto;
import com.example.personelservice.endpoint.rest.dto.LoginRequestDto;
import com.example.personelservice.endpoint.rest.dto.RefreshRequestDto;
import com.example.userservice.domain.service.AuthService;
import com.example.userservice.endpoint.rest.mapper.AuthResponseDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements DefaultApi {

    private final AuthService authService;
    private final AuthResponseDtoMapper authResponseDtoMapper;

    @Override
    public ResponseEntity<AuthResponseDto> authLoginPost(LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authResponseDtoMapper.toDto(
              authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword())));
    }

    @Override
    public ResponseEntity<AuthResponseDto> authRefreshPost(RefreshRequestDto refreshRequestDto) {
        return ResponseEntity.ok(authResponseDtoMapper.toDto(
              authService.refresh(refreshRequestDto.getRefreshToken())));
    }
}
