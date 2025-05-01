package com.example.qualificationsvc.endpoint.messaging.dto;

public enum EventTypes {
    PERSON_DELETED("PersonDeleted");
    private final String eventType;

    EventTypes(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}
