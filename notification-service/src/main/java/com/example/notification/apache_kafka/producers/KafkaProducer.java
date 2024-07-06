package com.example.notification.apache_kafka.producers;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
// DI by constructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaProducer {
//    // target is when create user sent message to Kafka, and notification service take this notification
//    // to public message into kafka using KafkaTemplate, including key and value, Using String as config yml
//    KafkaTemplate<String, User> kafkaTemplate;
//
//    public void sendUserRegisterSuccess(User user) {
//        log.info("Sending user register success message: {}", user);  // logging message before sending data to Kafka topic
//        // Producer, Publish message to kafka cluster
////        kafkaTemplate.send(KafkaTopicContain.USER_REGISTER_SUCCESS, "Welcome new member " + user.getUsername()); // producer , send message have datatype is string to kafka
//        kafkaTemplate.send(KafkaTopicContain.USER_REGISTER_SUCCESS, user); // to have data type is 'user', we have object to mapping json to 'user' as below
//        kafkaTemplate.setMessageConverter(new UserMessageConverter());
//    }
//}
}