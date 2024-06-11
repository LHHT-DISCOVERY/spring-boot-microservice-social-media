package com.example.identity.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
// initialize bean (@Component) to Feign Client scan Bean will add to using
// usually using request internal together among microservices (ex: identity service communication with : profile, post ,... etc)
// or configure manually for each microservice (no need mark annotation @Component), here we using configure
// if using @Component initialize bean, no need to do anything, or if using configure manual seen at ProfileClient class, keyword "configuration"
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) { // modify request before sent
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var authHeader = servletRequestAttributes.getRequest().getHeader("Authorization");
        log.info("Header :{}", authHeader);

        if (StringUtils.hasText(authHeader)) {
            requestTemplate.header("Authorization", authHeader);
        }
    }
}
