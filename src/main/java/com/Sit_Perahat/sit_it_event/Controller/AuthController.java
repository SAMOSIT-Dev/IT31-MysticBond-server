package com.Sit_Perahat.sit_it_event.Controller;


import com.Sit_Perahat.sit_it_event.Service.AuthService;
import com.Sit_Perahat.sit_it_event.dto.DefaultResponse;
import com.Sit_Perahat.sit_it_event.dto.LoginRequest;
import com.Sit_Perahat.sit_it_event.dto.LoginResponse;
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

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private AuthService authService;


    @RequestMapping("/login")
    public ResponseEntity<DefaultResponse> Auth(@RequestBody LoginRequest loginRequest) throws InterruptedException, IOException {

        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty() || loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
                errors.put("username", "username or password is empty");
            }
            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                errors.put("password", "password is empty");
            }

            DefaultResponse response = new DefaultResponse("Error", "Validation Failed", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {

            LoginResponse tokenResponse = authService.Login(loginRequest.getUsername(), loginRequest.getPassword());

            ResponseCookie refreshCookie = ResponseCookie.from("REFRESH_TOKEN", tokenResponse.getRefresh_token())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .sameSite("Strict")
                    .build();

            ResponseCookie accessCookies = ResponseCookie.from("ACCESS_TOKEN", tokenResponse.getAccess_token())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .sameSite("Strict")
                    .build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString(), accessCookies.toString()).body(new DefaultResponse("success", "Login Success", null));

        } catch (IOException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("IOException", "Token server not reachable");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new DefaultResponse("error", "Network error", errors));

        } catch (InterruptedException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("InterruptedException", "Login process was interrupted");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse("error", "Operation interrupted", errors));

        } catch (RuntimeException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("RuntimeException", "Invalid Credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new DefaultResponse("error", "Login failed", errors));
        }
    }

    @RequestMapping("/refresh")
    public ResponseEntity<DefaultResponse> refreshToken(@CookieValue(name = "REFRESH_TOKEN", required = false) String refreshToken)
            throws InterruptedException, IOException {

        if (refreshToken == null || refreshToken.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("refresh_token", "refresh token is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new DefaultResponse("error", "validation Failed",errors));
        }

        try {
            LoginResponse tokenResponse = authService.RefreshToken(refreshToken);


            ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", tokenResponse.getAccess_token())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                    .body(new DefaultResponse("success", "Use Refresh_token Success", null));


        }catch (IOException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("IOException", "Token server not reachable");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new DefaultResponse("error", "Network error", errors));

        } catch (InterruptedException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("InterruptedException", "Login process was interrupted");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse("error", "Operation interrupted", errors));

        } catch (RuntimeException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("RuntimeException", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new DefaultResponse("error", "Login failed", errors));
        }
    }
}
