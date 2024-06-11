package com.example.profile.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {
    // no need authenticate and authorization should be take all url in String[] PUBLIC_ENDPOINT and config url in
    // method filterChain and this endpoint access and no need token to authentication and authorization
    private final String[] PUBLIC_ENDPOINT = {"/internal/create"}; // endpoint "/internal" change to public to no need token but not exposed to api gateway

    CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(PUBLIC_ENDPOINT)
                .permitAll()
                //                 only permit endpoint and roles has admin authorization
                // or      .requestMatchers(ADMIN_ENDPOINT).hasRole(Role.ADMIN.name())
                //                .requestMatchers(ADMIN_ENDPOINT).hasAnyAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated());

        //  configured author and authentication api endpoint
        httpSecurity.oauth2ResourceServer(
                //                verify token -> authentication
                oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(customJwtDecoder)
                                // author with token
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        // using this authenticationEntryPoint() method if token invalid (this
                        // mean : authentication fail), where will we direct user go ? this EX: nothing redirect user ,
                        // only response code and message
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint()));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    // create been with JwtAuthenticationEntryPoint custom class
    @Bean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    //    customize converter - "SCOPE_ADMIN" default to "ROLE_ADMIN" custom. using in spring config security
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtAuthenticationConverter = new JwtGrantedAuthoritiesConverter();
        jwtAuthenticationConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();

        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwtAuthenticationConverter);
        return authenticationConverter;
    }


    @Bean
    public CorsFilter corsFilter() {
        // Cros only permitted call from the specific resource (do main)-> implement type preflight first -> then call
        // fetch.
        // seen F12 -> network
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // allow http://localhost:3000 to access
        corsConfiguration.addAllowedMethod("*"); // allow method access, access all methods
        corsConfiguration.addExposedHeader("*"); // allow header access, access all headers
        UrlBasedCorsConfigurationSource urlBasedCors = new UrlBasedCorsConfigurationSource();
        urlBasedCors.registerCorsConfiguration("/**", corsConfiguration); // apply cors for all endpoints
        return new CorsFilter(urlBasedCors);
    }
}
