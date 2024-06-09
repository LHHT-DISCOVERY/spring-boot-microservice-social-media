package com.example.identity.configuration;

import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.example.identity.dto.request.IntrospectTokenRequest;
import com.example.identity.service.impl.AuthenticationService;
import com.nimbusds.jose.JOSEException;

//  this class response for verify token -> using token now that implement tasks need author
//  ex: get list user need a token have role admin to do this task
@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value(value = "${jwt.signKey}")
    private String signKey;

    @Autowired
    private AuthenticationService authenticationService;

    NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        //        before nimbusJwtDecoder, need checking the token has been expired or logout yet?
        try {
            var response = authenticationService.introspect(
                    IntrospectTokenRequest.builder().token(token).build());
            // if token invalid throw and no need implement nimbus below
            if (!response.isValid()) throw new JwtException("Token invalid");
        } catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return nimbusJwtDecoder.decode(token);
    }
}
