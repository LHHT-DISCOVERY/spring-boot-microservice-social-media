package com.example.notification.repository.httpClient;

import com.example.notification.dto.request.EmailRequest;
import com.example.notification.dto.response.EmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "email-client", url = "${email.third-party-uri}")
public interface EmailClient {
    // curl --request POST
    //     --url https://api.brevo.com/v3/smtp/email
    //     --header 'accept: application/json'
    //     --header 'api-key: .........'
    //     --header 'content-type: application/json' -> need to declare method as below
    @PostMapping(value = "/v3/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
    EmailResponse sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody EmailRequest request);
}
