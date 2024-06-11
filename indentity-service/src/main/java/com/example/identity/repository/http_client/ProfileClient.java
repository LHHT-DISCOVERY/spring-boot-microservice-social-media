package com.example.identity.repository.http_client;
// create this interface to communicate with the server profile service

import com.example.identity.configuration.AuthenticationRequestInterceptor;
import com.example.identity.dto.request.ProfileCreateRequest;
import com.example.identity.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// to 2 microservices communication by http (OpenFeign)
@FeignClient(name = "profile-service", url = "${app.service.profile}",
        configuration = {AuthenticationRequestInterceptor.class})
// what do we want to use Interceptor at Feign Client?, we using "configuration"
public interface ProfileClient {
    @PostMapping(value = "/internal/create", produces = MediaType.APPLICATION_JSON_VALUE)
    // using post method, and json value in request body
    UserProfileResponse createProfile(@RequestBody ProfileCreateRequest request);
    // when call api to profile service, then profile service need to have a token to authentication , if not, security in profile service will prevent
    // to do these things , using a AuthenticationRequestInterceptor class to Feign Client scan and add more a token in Header,
    // then profile service get to token implement task in profile service
}
