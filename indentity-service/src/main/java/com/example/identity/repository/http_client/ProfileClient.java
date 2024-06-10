package com.example.identity.repository.http_client;
// create this interface to communicate with the server profile service

import com.example.identity.dto.request.ProfileCreateRequest;
import com.example.identity.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.service.profile}") // to 2 microservices communication by http (OpenFeign)
public interface ProfileClient {
    @PostMapping(value = "/internal/create", produces = MediaType.APPLICATION_JSON_VALUE) // using post method , and json value in request body
    UserProfileResponse createProfile(@RequestBody ProfileCreateRequest request);
}
