package com.example.qualificationsvc.domain.port;

import com.example.qualificationsvc.domain.model.QualificationProfile;

public interface QualificationEventProvider {

    void sendQualificationEvent(QualificationProfile qualificationProfile);
}
