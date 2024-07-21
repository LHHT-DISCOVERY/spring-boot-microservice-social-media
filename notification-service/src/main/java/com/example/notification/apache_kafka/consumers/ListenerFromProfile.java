package com.example.notification.apache_kafka.consumers;


import com.example.event.dto.NotificationEvent;
import com.example.notification.apache_kafka.kafka_topics.KafkaTopicContain;
import com.example.notification.dto.request.EmailFormat;
import com.example.notification.dto.request.SendEmailRequest;
import com.example.notification.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@KafkaListener(topics = {KafkaTopicContain.PROFILE_USER_CREATE_PROFILE_SUCCESS})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ListenerFromProfile {

    ObjectMapper objectMapper;
    EmailService emailService;

    @KafkaHandler
    public void handelProfileCreateProfileSuccess(NotificationEvent message) {
        SendEmailRequest request = SendEmailRequest.builder()
                .to(List.of(new EmailFormat(message.getRecipient(), message.getRecipient())))
                .subject(message.getSubject())
                .htmlContent(message.getBody())
                .build();
        emailService.sendEmail(request);
        log.info("Received message from profile server: {} and send mail to user successful", message);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Received unknown message at listen from profile server: {}", object);
    }
}
