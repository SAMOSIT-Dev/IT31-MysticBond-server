package com.Sit_Perahat.sit_it_event.SecurtyConfig;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String message = "Unauthorized - Bearer token is missing or invalid";

        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        if (exception != null) {
            message = exception.getMessage();
        } else if (authException != null) {
            message = authException.getMessage();
        }

        String json = String.format("{\"error\": \"%s\"}", message);
        response.getWriter().write(json);
    }
}
