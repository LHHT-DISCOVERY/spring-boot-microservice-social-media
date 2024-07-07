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
                .htmlContent("<h1 style=\"margin: auto; margin-top: 20px; border: 1px solid #indigo; text-align: center;\">Hello " + genderUser + userProfile.getFirstName() + " " +  userProfile.getLastName() + " </h1> </br> " +
                        "<h3  style=\"color: #dd3f05!important; font-weight: bold; padding: 15px 0; color: #333; text-align: center;\"> CONGRATULATE RECEIVED A COUPON CODE FOR NEW USER : uyw23er&h23  </h3> </br> " +
                        "<h4 style=\"width: 70%; text-align: center;margin:auto\"> Welcome to eSportFlick, the premier social network designed exclusively for gamers. </br> " +
                        "Whether you're a casual player or a hardcore enthusiast. </br> " +
                        "eSportFlick offers a vibrant platform where you can connect, share, " +
                        "and discover new gaming experiences. </h4> </br> " +
                        "<h4  style=\"padding: 15px 0; color: #333; text-align: center;\">NOTE THAT: let's contact FaceBook: LY HUYNH HUU TRI, if you have concern about us, </h4>  </br> <h4 style=\"padding: 15px 0; color: red; text-align: center;\">Follow link at: https://www.facebook.com/lyshuynhshuustris.tris/</h4>")
                .build();
        emailService.sendEmail(request);
        log.info("Received message from profile server: {} and send mail to user successful", request);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Received unknown message at listen from profile server: {}", object);
    }
}
