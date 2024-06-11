package com.example.gateway.repository;

import com.example.gateway.dto.request.IntrospectTokenRequest;
import com.example.gateway.dto.respose.ApiResponse;
import com.example.gateway.dto.respose.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@Repository
// call server identity
public interface IdentityClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
        // using post exchange , this is http of spring, not of OpenFeign,
        // this thing is required call to identity services because server gateway need server identity verify token for api gateway
        // this is pattern using popular (focus on authentication by using verify token in api gateway)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectTokenRequest request); // call not using OpenFeign
}
