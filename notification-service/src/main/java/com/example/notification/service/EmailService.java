package com.example.notification.service;

import com.example.notification.dto.request.EmailFormat;
import com.example.notification.dto.request.EmailRequest;
import com.example.notification.dto.request.SendEmailRequest;
import com.example.notification.dto.response.EmailResponse;
import com.example.notification.exception.AppException;
import com.example.notification.exception.ErrorCode;
import com.example.notification.repository.httpClient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @Value(value = "${email.apiKeyEmail}")
    @NonFinal
    String apiKeyEmail;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(EmailFormat.builder()
                        .name("Dev TRI")
                        .email("lytri103@gmail.com")
                        .build())
                .to(request.getTo())
                .htmlContent(request.getHtmlContent())
                .subject(request.getSubject())
                .build();
        try {

            return emailClient.sendEmail(apiKeyEmail,emailRequest);
        } catch (FeignException.FeignClientException e) {
            throw new AppException(ErrorCode.CAN_NOT_SENT_EMAIL);
        }
    }
}
