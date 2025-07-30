package com.Sit_Perahat.sit_it_event.Fitler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Component
public class JwtCookieFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("auth".equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();

                    String decodedValue = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8.name());

                    try {
                        JsonNode root = objectMapper.readTree(decodedValue);

                        if (root.has("ACCESS_TOKEN")) {
                            String accessToken = root.get("ACCESS_TOKEN").asText();

                            HttpServletRequest wrapped = new HttpServletRequestWrapper(request) {
                                @Override
                                public String getHeader(String name) {
                                    if ("Authorization".equalsIgnoreCase(name)) {
                                        return "Bearer " + accessToken;
                                    }
                                    return super.getHeader(name);
                                }

                                @Override
                                public Enumeration<String> getHeaders(String name) {
                                    if ("Authorization".equalsIgnoreCase(name)) {
                                        return Collections.enumeration(List.of("Bearer " + accessToken));
                                    }
                                    return super.getHeaders(name);
                                }
                            };

                            filterChain.doFilter(wrapped, response);
                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
