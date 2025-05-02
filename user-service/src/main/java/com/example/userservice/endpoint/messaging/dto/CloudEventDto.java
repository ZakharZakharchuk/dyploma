package com.example.userservice.endpoint.messaging.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import lombok.Data;

@Data
public class CloudEventDto {

    private String id;
    private Date time;
    private String type;
    @JsonRawValue
    private String data;

    @JsonSetter("data")
    public void setRawDataPayload(JsonNode jsonNode) throws JsonProcessingException {
        setData(new ObjectMapper().writeValueAsString(jsonNode));
    }
}
