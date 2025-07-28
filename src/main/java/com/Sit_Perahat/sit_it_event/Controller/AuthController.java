package com.Sit_Perahat.sit_it_event.Controller;


import com.Sit_Perahat.sit_it_event.Service.AuthService;
import com.Sit_Perahat.sit_it_event.Service.UsersService;
import com.Sit_Perahat.sit_it_event.dto.DefaultResponse;
import com.Sit_Perahat.sit_it_event.dto.LoginRequest;
import com.Sit_Perahat.sit_it_event.dto.LoginResponse;
import com.Sit_Perahat.sit_it_event.dto.RefreshToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsersService usersService;

    @RequestMapping("/login")
    public ResponseEntity<DefaultResponse> Auth(@Valid @RequestBody LoginRequest loginRequest) {

        try {

            usersService.findUser(loginRequest.getStudentId());

            LoginResponse tokenResponse = authService.Login(loginRequest.getStudentId(), loginRequest.getPassword());

            Map<String, Object> response = new HashMap<>();
            response.put("ACCESS_TOKEN", tokenResponse.getAccess_token());
            response.put("REFRESH_TOKEN",tokenResponse.getRefresh_token());
            return ResponseEntity.ok().body(new DefaultResponse("success", "Login Success", response));

        } catch (IOException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("IOException", "Token server not reachable");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new DefaultResponse("error", "Network error", errors));

        } catch (InterruptedException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("InterruptedException", "Login process was interrupted");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse("error", "Operation interrupted", errors));

        }catch (RuntimeException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("RuntimeException", "Invalid Credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new DefaultResponse("error", "Login failed", errors));
        }
    }

    @RequestMapping("/refresh")
    public ResponseEntity<DefaultResponse> refreshToken(@Valid @RequestBody RefreshToken refreshToken) {

        if (refreshToken.getRefresh_token() == null ||  refreshToken.getRefresh_token().isEmpty()) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("refresh_token", "refresh token is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new DefaultResponse("error", "validation Failed",errors));
        }

        try {
            LoginResponse tokenResponse = authService.RefreshToken(refreshToken.getRefresh_token());
            Map<String, Object> response = new HashMap<>();
            response.put("ACCESS_TOKEN", tokenResponse.getAccess_token());
            response.put("REFRESH_TOKEN",tokenResponse.getRefresh_token());
            return ResponseEntity.ok()
                    .body(new DefaultResponse("success", "Use Refresh_token Success", response));


        }catch (IOException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("IOException", "Token server not reachable");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new DefaultResponse("error", "Network error", errors));
        } catch (InterruptedException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("InterruptedException", "Login process was interrupted");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse("error", "Operation interrupted", errors));

        } catch (RuntimeException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("RuntimeException", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new DefaultResponse("error", "Refresh Token expired", errors));
        }
    }
}
