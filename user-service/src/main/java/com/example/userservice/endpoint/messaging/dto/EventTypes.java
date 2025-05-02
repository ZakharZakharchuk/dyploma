package com.example.userservice.endpoint.messaging.dto;

public enum EventTypes {
    USER_CREATED("UserCreated");
    private final String eventType;

    EventTypes(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}
