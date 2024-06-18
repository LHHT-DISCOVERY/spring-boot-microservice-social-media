package com.example.notification.configuration;

import com.example.notification.dto.response.ApiResponse;
import com.example.notification.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // Authenticated here, this mean token invalid or token has been logout
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        //        write object to String , now that return String
        String contentReturn = objectMapper.writeValueAsString(apiResponse);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //        put content into the returned content from api with token invalid (this mean: authentication with jwt
        // not successful )
        response.getWriter().write(contentReturn);
        //        commit response
        response.flushBuffer();
    }
}
