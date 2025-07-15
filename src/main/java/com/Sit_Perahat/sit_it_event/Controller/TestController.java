package com.Sit_Perahat.sit_it_event.Controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    private String hello(Authentication authentication){
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String token = jwt.getClaimAsString("preferred_username");

        return "Hello World! " + token;
    }
}
