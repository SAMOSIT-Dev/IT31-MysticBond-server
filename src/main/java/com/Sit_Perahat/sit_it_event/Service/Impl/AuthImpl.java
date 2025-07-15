package com.Sit_Perahat.sit_it_event.Service.Impl;

import com.Sit_Perahat.sit_it_event.Service.AuthService;
import com.Sit_Perahat.sit_it_event.dto.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


@Service
public class AuthImpl implements AuthService {


    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Value("${TOKEN_URL}")
    private String tokenUrl;

    @Override
    public LoginResponse Login(String username, String password) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String form = new StringBuilder()
                .append("grant_type=password")
                .append("&client_id=").append(URLEncoder.encode(clientId, StandardCharsets.UTF_8))
                .append("&client_secret=").append(URLEncoder.encode(clientSecret, StandardCharsets.UTF_8))
                .append("&username=").append(URLEncoder.encode(username, StandardCharsets.UTF_8))
                .append("&password=").append(URLEncoder.encode(password, StandardCharsets.UTF_8))
                .append("&scope=").append(URLEncoder.encode("openid profile", StandardCharsets.UTF_8))
                .toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tokenUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            LoginResponse loginResponse = mapper.readValue(response.body(), LoginResponse.class);
            return loginResponse;
        } else {
            throw new RuntimeException("Failed to get token: " + response.body());
        }
    }
}
