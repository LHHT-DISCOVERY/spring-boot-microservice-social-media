package com.example.notification.apache_kafka.consumers;



import com.example.notification.apache_kafka.kafka_topics.KafkaTopicContain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
// to declare consumer , using annotation @KafkaListener, param "Topics" and "groupId"
//  but was configured in yml -> no need to using param group
//  -> need to using group to void that messages sent all instances -> duplicate message -> mistake in process
@org.springframework.kafka.annotation.KafkaListener(topics = {KafkaTopicContain.IDENTITY_USER_REGISTER_SUCCESS})
// can have multiple topics para in bracket by ","
public class ListenerFromIdentity {

    @KafkaHandler
    public String listenUserRegister(String message) { // when have message come, map message received into param "String message" in method
        log.info("Received message from identity-service: {}", message);
        return message;
    }


    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Received unknown message: {}", object);
    }
}
