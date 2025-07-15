package com.Sit_Perahat.sit_it_event.Controller;


import com.Sit_Perahat.sit_it_event.Service.AuthService;
import com.Sit_Perahat.sit_it_event.dto.ErrorResponse;
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

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private AuthService authService;


    @RequestMapping("/login")
    public ResponseEntity<?> Auth(@RequestBody LoginRequest loginRequest) throws InterruptedException, IOException {

        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty() ||
                loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("error", "Username or Password is empty"));
        }

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


        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString(), accessCookies.toString()).body("Login Success");
    }

    @RequestMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "REFRESH_TOKEN", required = false) String refreshToken)
            throws InterruptedException, IOException {

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("error", "Refresh Token is empty"));
        }

        LoginResponse tokenResponse = authService.RefreshToken(refreshToken);


        ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", tokenResponse.getAccess_token())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE , accessCookie.toString())
                .body("Use Refresh Token Success");
    }
}
