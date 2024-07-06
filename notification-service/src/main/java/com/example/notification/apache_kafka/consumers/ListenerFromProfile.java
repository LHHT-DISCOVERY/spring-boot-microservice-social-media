package com.example.notification.apache_kafka.consumers;

import com.example.notification.apache_kafka.kafka_topics.KafkaTopicContain;
import com.example.notification.dto.request.EmailFormat;
import com.example.notification.dto.request.SendEmailRequest;
import com.example.notification.model.UserProfile;
import com.example.notification.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public void handelProfileCreateProfileSuccess(String message) throws JsonProcessingException {
        UserProfile userProfile = objectMapper.readValue(message, UserProfile.class);
        String genderUser = userProfile.getGender().equals("male") ? "Mr. " : "Ms. ";
        log.info("message : {}" , message);
        log.info("Email Address : {}" , userProfile.getEmail());
        SendEmailRequest request = SendEmailRequest.builder()
                .to(List.of(new EmailFormat(userProfile.getLastName(), userProfile.getEmail())))
                .subject("HELLO " + genderUser + userProfile.getLastName() + " - WELCOME TO ONBOARD ")
                .htmlContent("<h4>Hello " + genderUser + userProfile.getLastName() + " </h4> </br> " +
                        "<p> Welcome to eSportFlick, the premier social network designed exclusively for gamers. " +
                        "Whether you're a casual player or a hardcore enthusiast, " +
                        "eSportFlick offers a vibrant platform where you can connect, share, " +
                        "and discover new gaming experiences. </p>")
                .build();
        emailService.sendEmail(request);
        log.info("Received message from profile server: {} and send mail to user successful", request);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Received unknown message at listen from profile server: {}", object);
    }
}
