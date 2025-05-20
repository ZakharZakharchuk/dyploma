package com.example.candidatelistsvc.domain.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.candidatelistsvc.endpoint.rest.dto.BaseErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {

    private static final String UNAUTHORIZED_ERROR_TYPE = "UNAUTHORIZED";
    private static final String UNAUTHORIZED_ERROR_TITLE = "Access denied";
    private static final String VALIDATION_ERROR_TYPE = "VALIDATION_ERROR";
    private static final String VALIDATION_ERROR_TITLE = "Request failed validation";
    private static final String UNEXPECTED_ERROR_TYPE = "Unexpected server error";
    private static final String UNEXPECTED_ERROR_TITLE = "Internal server error";

    private final String serviceName;

    public GeneralExceptionHandler(@Value("${spring.application.name}") String serviceName) {
        this.serviceName = serviceName;
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<BaseErrorResponseDto> handleUnauthorizedAccessException(
          HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
              .contentType(MediaType.APPLICATION_PROBLEM_JSON)
              .body(BaseErrorResponseDto.builder()
                    .type(UNAUTHORIZED_ERROR_TYPE)
                    .title(UNAUTHORIZED_ERROR_TITLE)
                    .instance(request.getRequestURI())
                    .build());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<BaseErrorResponseDto> handleObjectNotFoundException(
          Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND)
              .contentType(MediaType.APPLICATION_PROBLEM_JSON)
              .body(BaseErrorResponseDto.builder()
                    .type(NOT_FOUND.name())
                    .title(ex.getMessage())
                    .instance(request.getRequestURI())
                    .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingRequestHeaderException.class,
          HttpMessageNotReadableException.class, HttpMediaTypeException.class})
    public ResponseEntity<BaseErrorResponseDto> handleValidationException(
          MethodArgumentNotValidException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .contentType(MediaType.APPLICATION_PROBLEM_JSON)
              .body(BaseErrorResponseDto.builder()
                    .type(VALIDATION_ERROR_TYPE)
                    .title(VALIDATION_ERROR_TITLE)
                    .instance(request.getRequestURI())
                    .details(exception.getBindingResult().toString())
                    .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorResponseDto> handleUnexpectedException(
          Exception exception, HttpServletRequest request) {
        log.error("Unexpected error occurred", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .contentType(MediaType.APPLICATION_PROBLEM_JSON)
              .body(BaseErrorResponseDto.builder()
                    .type(UNEXPECTED_ERROR_TYPE)
                    .title(UNEXPECTED_ERROR_TITLE)
                    .instance(request.getRequestURI())
                    .details(exception.getMessage())
                    .build());
    }
}
