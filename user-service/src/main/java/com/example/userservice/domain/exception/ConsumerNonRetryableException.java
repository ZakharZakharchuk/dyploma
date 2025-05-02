package com.example.userservice.domain.exception;

public class ConsumerNonRetryableException extends RuntimeException {

    public ConsumerNonRetryableException(String message) {
        super(message);
    }

    public ConsumerNonRetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}
