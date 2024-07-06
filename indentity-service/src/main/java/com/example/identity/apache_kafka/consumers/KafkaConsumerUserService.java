package com.example.identity.apache_kafka.consumers;


import com.example.identity.apache_kafka.kafka_topics.KafkaTopicContain;
import com.example.identity.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
// to declare consumer , using annotation @KafkaListener, param "Topics" and "groupId"
//  but was configured in yml -> no need to using param group
//  -> need to using group to void that messages sent all instances -> duplicate message -> mistake in process
@org.springframework.kafka.annotation.KafkaListener(topics = {KafkaTopicContain.DATA_USER_REGISTER_SUCCESS})
// can have multiple topics para in bracket by ","
public class KafkaConsumerUserService {

    @KafkaHandler
    public void listenUserRegisterFromIdentityServer(UserResponse userResponse) { // when have message come, map message received into param "UserResponse userResponse" in method
        log.info("Received message at server identity with username of object: {}", userResponse.toString());
    }


    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Received unknown message: {}", object);
    }
}
