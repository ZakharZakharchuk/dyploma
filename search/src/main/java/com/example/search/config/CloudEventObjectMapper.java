package com.example.search.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudEventObjectMapper {

    public static final String CLOUD_EVENT_OBJECT_MAPPER = "cloudEventMapper";

    @Bean
    @Qualifier(CLOUD_EVENT_OBJECT_MAPPER)
    public ObjectMapper cloudEventMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Register JavaTimeModule to handle Instant, OffsetDateTime, etc.
        objectMapper.registerModule(new JavaTimeModule());

        // Configure to serialize dates as ISO-8601 (not timestamps)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        // Optionally ignore unknown properties (good for flexible APIs)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }
}