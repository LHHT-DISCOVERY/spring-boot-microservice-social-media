package com.example.identity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
// using for api need authenticate and authorization + ex: annotation @PreAuthorize("hasRole('ADMIN')") above method
// need authenticated and author in service layer
public class SecurityConfig {
    // no need authenticate and authorization should be take all url in String[] PUBLIC_ENDPOINT and config url in
    // method filterChain
    private final String[] PUBLIC_ENDPOINT = {"/users/registrations", "/auth/**", "/permissions/**", "/roles/**"
    };
    private final String[] ADMIN_ENDPOINT = {"/users/list"};

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

        //        using request, to provide token then this jwt will implements authenticate using task by url
        // configured endpoint in spring security
        httpSecurity.oauth2ResourceServer(
                //                verify token
                oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(customJwtDecoder)
                                //                        author with token
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
        jwtAuthenticationConverter.setAuthorityPrefix(""); // because set "ROLE_" in authentication service class

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();

        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwtAuthenticationConverter);
        return authenticationConverter;
    }

    //  this method -> response for verify token -> using token now that implement tasks need author
    //  ex: get list user need a token have role "admin" to do this task
    //    @Bean
    //    JwtDecoder jwtDecoder() {
    //        SecretKeySpec secretKeySpec = new SecretKeySpec(signKey.getBytes(), "HS512");
    //        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    //    }

    //    instead of : PasswordEncoder passWordEncoder = new BCryptPasswordEncoder(10) in each classes, we using @Bean
    // following as below
    //    DI in other class essential, example: AuthenticationService class
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
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
