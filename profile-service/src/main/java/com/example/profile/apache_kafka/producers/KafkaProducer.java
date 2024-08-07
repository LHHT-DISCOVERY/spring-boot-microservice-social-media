package com.example.profile.apache_kafka.producers;

import com.example.profile.apache_kafka.kafka_topics.KafkaTopicContain;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaProducer {
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessageCreateProfileUserSuccess(Object object) {
        kafkaTemplate.send(KafkaTopicContain.PROFILE_CREATE_USER_SUCCESS, object);
        log.info("Sending message: {}", object);
    }

}
