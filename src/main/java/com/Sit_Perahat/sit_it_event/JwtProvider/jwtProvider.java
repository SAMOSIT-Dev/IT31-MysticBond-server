package com.Sit_Perahat.sit_it_event.JwtProvider;


import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class jwtProvider {


    public String getUserNameFromAuthentication(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaimAsString("preferred_username");
    }


}
