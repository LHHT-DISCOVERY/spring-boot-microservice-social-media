package com.example.identity.apache_kafka.producers;

import com.example.identity.apache_kafka.kafka_topics.KafkaTopicContain;
import com.example.identity.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaJsonProducerUserService {
    KafkaTemplate<String, UserResponse> kafkaTemplate;

    public void sendMessageUserRegisterSuccess(UserResponse userResponse) {
        Message<UserResponse> message = MessageBuilder
                .withPayload(userResponse)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopicContain.DATA_USER_REGISTER_SUCCESS)
                .build();
        kafkaTemplate.send(message);
    }
}
