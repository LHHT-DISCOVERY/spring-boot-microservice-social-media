package com.example.identity.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identity.common.event_tracking.AuditEventType;
import com.example.identity.common.event_tracking.AuditLogger;
import com.example.identity.common.event_tracking.AuditStatus;
import com.example.identity.dto.request.AuthenticationRequest;
import com.example.identity.dto.request.IntrospectTokenRequest;
import com.example.identity.dto.request.LogoutRequest;
import com.example.identity.dto.request.RefreshTokenRequest;
import com.example.identity.dto.response.ApiResponse;
import com.example.identity.dto.response.AuthenticationResponse;
import com.example.identity.dto.response.IntrospectResponse;
import com.example.identity.service.impl.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/v1/public/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationService.authenticated(authenticationRequest);
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(result);
        AuditLogger.info(
                "System Create Token", AuditEventType.LOGIN, AuditStatus.SUCCESS, "Call API /v1/public/auth/token");
        return apiResponse;
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectTokenRequest introspectTokenRequest)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(introspectTokenRequest);
        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(result);
        return apiResponse;
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest introspectTokenRequest) throws ParseException, JOSEException {
        authenticationService.logout(introspectTokenRequest);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        AuditLogger.info(
                "System Create Token", AuditEventType.LOGOUT, AuditStatus.SUCCESS, "Call API /v1/public/auth/logout");
        return apiResponse;
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(result);
        AuditLogger.info(
                "System Create Token", AuditEventType.LOGIN, AuditStatus.SUCCESS, "Call API /v1/public/auth/refresh");
        return apiResponse;
    }
}
