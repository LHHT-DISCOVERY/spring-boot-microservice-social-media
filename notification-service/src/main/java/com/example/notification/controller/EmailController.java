package com.example.notification.controller;

import com.example.notification.dto.request.SendEmailRequest;
import com.example.notification.dto.response.ApiResponse;
import com.example.notification.dto.response.EmailResponse;
import com.example.notification.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    ApiResponse<EmailResponse> emailResponse(@RequestBody SendEmailRequest request) {
        ApiResponse<EmailResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(emailService.sendEmail(request));
        return apiResponse;
    }
}
