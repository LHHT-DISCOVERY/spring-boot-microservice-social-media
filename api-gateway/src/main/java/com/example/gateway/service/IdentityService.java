package com.example.gateway.service;

import com.example.gateway.dto.request.IntrospectTokenRequest;
import com.example.gateway.dto.respose.ApiResponse;
import com.example.gateway.dto.respose.IntrospectResponse;
import com.example.gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// call repo and repo will call server identity
public class IdentityService {
    IdentityClient identityClient;
    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        return identityClient.introspect(IntrospectTokenRequest.builder().token(token).build());
    }
}
