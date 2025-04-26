package com.example.search.config;

import com.example.search.domain.exception.ConsumerNonRetryableException;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.*;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class KafkaConfig {

    private static final int DLQ_TOPIC_PARTITION = -1;

    @Autowired
    private CloudEventProperties cloudEventProperties;

    private Map<String, String> consumerTopics;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> consumerKafkaListenerContainerFactory(
          ConsumerFactory<String, String> consumerFactory,
          DefaultErrorHandler dlqCommonErrorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(dlqCommonErrorHandler);
        return factory;
    }

    @Bean
    public DefaultErrorHandler dlqCommonErrorHandler(ConsumerRecordRecoverer recoverer,
          BackOff backOff) {
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);
        //errorHandler.addRetryableExceptions(RuntimeException.class);
        errorHandler.addNotRetryableExceptions(
              RuntimeException.class,
              ConsumerNonRetryableException.class,
              IllegalStateException.class,
              IllegalArgumentException.class,
              NullPointerException.class,
              ClassCastException.class,
              ClassNotFoundException.class);
        return errorHandler;
    }

    @Bean
    public BackOff backOff() {
        return new FixedBackOff(cloudEventProperties.getRetryInterval(),
              cloudEventProperties.getMaxAttempts());
    }

    @Bean
    public ConsumerRecordRecoverer dlqCommonRecoverer(KafkaTemplate<String, String> kafkaTemplate) {
        return new DeadLetterPublishingRecoverer(
              kafkaTemplate,
              (cr, e) -> {
                  var cause = Optional.ofNullable(e.getCause()).orElse(e);
                  var errorMessage = Optional.ofNullable(cause.getMessage())
                        .orElseGet(e::getMessage);
                  var dlqTopic = resolveDlqTopic(cr.topic());
                  //TODO logs
                  return new TopicPartition(dlqTopic, DLQ_TOPIC_PARTITION);
              }
        );
    }

    private String resolveDlqTopic(String eventTopicName) {
        return consumerTopics.get(eventTopicName);
    }

    @PostConstruct
    protected void loadConsumerTopics() {
        this.consumerTopics = new HashMap<>();
        consumerTopics.put(cloudEventProperties.getPerson().getReceiveTopic(),
              cloudEventProperties.getPerson().getDlqTopic());
        consumerTopics.put(cloudEventProperties.getQualification().getReceiveTopic(),
              cloudEventProperties.getQualification().getDlqTopic());
    }
}
