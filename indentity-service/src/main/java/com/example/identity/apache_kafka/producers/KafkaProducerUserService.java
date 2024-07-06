package com.example.identity.apache_kafka.producers;

import com.example.identity.apache_kafka.kafka_topics.KafkaTopicContain;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
// DI by constructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaProducerUserService {
    // target is when create user sent message to Kafka, and notification service take this notification
    // to public message into kafka using KafkaTemplate, including key and value, Using String as config yml
    // each topic should contain a unique datatype
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageUserRegisterSuccess(String message) { // content of message is String
        log.info("Sending user register success message: {}", message);  // logging message before sending data to Kafka topic
        // producer , send message have datatype is string to kafka
        kafkaTemplate.send(KafkaTopicContain.MESSAGE_USER_REGISTER_SUCCESS, message);
    }
}
