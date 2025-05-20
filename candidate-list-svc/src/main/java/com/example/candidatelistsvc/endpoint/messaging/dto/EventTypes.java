package com.example.candidatelistsvc.endpoint.messaging.dto;

public enum EventTypes {
    PERSON_UPDATED("PersonUpdated"),
    PERSON_DELETED("PersonDeleted"),
    QUALIFICATION_UPDATED("QualificationUpdated");
    private final String eventType;

    EventTypes(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}
