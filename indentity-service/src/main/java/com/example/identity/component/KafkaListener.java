package com.example.identity.component;


import com.example.identity.common.util.KafkaTopicContain;
import com.example.identity.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
// to declare consumer , using annotation @KafkaListener, param "Topics" and "groupId"
//  but was configured in yml -> no need to using param group
//  -> need to using group to void that messages sent all instances -> duplicate message -> mistake in process
@org.springframework.kafka.annotation.KafkaListener(topics = {KafkaTopicContain.USER_REGISTER_SUCCESS})
// can have multiple topics para in bracket by ","
public class KafkaListener {

    @KafkaHandler
    public void listenUserRegisterFromIdentityServer(User message) { // when have message come, map message received into param "String message" in method
        log.info("Received message at server identity: {}", message.getUsername());
    }


    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Received unknown message: {}", object);
    }
}
