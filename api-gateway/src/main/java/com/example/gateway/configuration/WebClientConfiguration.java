package com.example.gateway.configuration;

import com.example.gateway.repository.IdentityClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
// because using @PostExchange in repo, not using FeignClient ,so need this class to can call to server identity
public class WebClientConfiguration {
    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8080/identity").build();
    }

    @Bean
        // initialize bean for IdentityClient from repository
    IdentityClient identityClient(WebClient webClient) { // get bean WebClient above
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();

        return httpServiceProxyFactory.createClient(IdentityClient.class);
    }
}
