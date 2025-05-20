package com.example.qualificationsvc.domain.exception;

import java.text.MessageFormat;

public class ObjectNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE_FORMAT = "{0} not found";

    public ObjectNotFoundException(String message) {
        super(MessageFormat.format(ERROR_MESSAGE_FORMAT, message));
    }
}
