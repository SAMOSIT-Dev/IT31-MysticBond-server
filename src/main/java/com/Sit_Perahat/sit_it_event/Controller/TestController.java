package com.Sit_Perahat.sit_it_event.Controller;


import com.Sit_Perahat.sit_it_event.JwtProvider.jwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private jwtProvider jwtProvider;

    @GetMapping("/hello")
    private String hello(Authentication authentication){

        String username = jwtProvider.getUserNameFromAuthentication(authentication);
        return "Hello World! " + username;
    }
}
