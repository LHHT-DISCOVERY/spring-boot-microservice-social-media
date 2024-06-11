package com.example.gateway.configuration;

import com.example.gateway.dto.respose.ApiResponse;
import com.example.gateway.service.IdentityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

// using spring cloud reactive server
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    @NonFinal
    String[] publicEndpoints = {"/identity/auth/.*", "/identity/users/registrations"};// configuration for api endpoints we want to public , no need to authenticate; using regex

    @NonFinal
    @Value(value = "${app.api-prefix}")
    private String prefix;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("enter authentication filter "); // when we implement request thought gateway -> into this log

        if ((isEndpointPublic(exchange.getRequest()))) { // check if endpoint have public
            return chain.filter(exchange); // if true -> pass -> can call to downstream service
        }


        // Get token from authorization header
        List<String> autHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);// get Header from ServerWebExchange
        if (CollectionUtils.isEmpty(autHeader)) { // if haven't  header,using CollectionUtils to check List have empty or not
            return unauthenticated(exchange.getResponse());
        }

        String token = autHeader.get(0).replace("Bearer ", "");
        log.info("Token: " + token);

        // verity token
        // delegate to identity service
        return identityService.introspect(token).flatMap(introspectResponseApiResponse -> {
            if (introspectResponseApiResponse.getResult().isValid()) {
                // continue filter chain -> can call to downstream service
                return chain.filter(exchange);
            } else {
                return unauthenticated(exchange.getResponse());
            }
            // onErrorResume() method, if any error caused in future. ex:503,disconnect,...-> throw unauthenticated method to prevent
        }).onErrorResume(throwable -> unauthenticated(exchange.getResponse())); // introspectResponseApiResponse is apiResponse
        // because using http of "post exchange" in spring 6("@PostExchange" show repo), not using FeignClient
        // -> need to declare WebClientConfiguration class in package "configuration" the we can call to server identity

    }

    @Override
    public int getOrder() { // sort global filter (number as small  , then number as more as max)
        return -1; // filters of cloud gateway > 0, set -1 to secure run before
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse<?> apiResponse = ApiResponse.builder().code(1004).message("API Gateway Unauthenticated").build();
        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        // if response is string , wen want to return json object value should use getHeaders().add(...) method
        // add more Header is content_type into response to return object json in returned body, not string
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    private boolean isEndpointPublic(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoints).anyMatch(s ->
                request.getURI().getPath().matches(prefix + s)); // s is publicEndpoints
    }
}
