package com.Sit_Perahat.sit_it_event.Controller;


import com.Sit_Perahat.sit_it_event.Entity.Users;
import com.Sit_Perahat.sit_it_event.JwtProvider.jwtProvider;
import com.Sit_Perahat.sit_it_event.Service.UsersService;
import com.Sit_Perahat.sit_it_event.dto.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final jwtProvider jwtProvider;

    @GetMapping
    public ResponseEntity<DefaultResponse> getUsers(Authentication authentication) {
        String studentId = jwtProvider.getUserNameFromAuthentication(authentication);
        try{
            Map<String, Object> response = new HashMap<>();
            Users user = usersService.findUser(studentId);
            response.put("user", user);
            return  ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse("success","Retrieve Users By Id " + studentId,response));
        }catch (RuntimeException e){
            Map<String, Object> errors = new HashMap<>();
            errors.put("RuntimeException", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse("error", "Not found Users By id " + studentId, errors));
        }

    }
}
