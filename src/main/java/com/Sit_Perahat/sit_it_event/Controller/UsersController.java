package com.Sit_Perahat.sit_it_event.Controller;


import com.Sit_Perahat.sit_it_event.Entity.Houses;
import com.Sit_Perahat.sit_it_event.Entity.Users;
import com.Sit_Perahat.sit_it_event.JwtProvider.jwtProvider;
import com.Sit_Perahat.sit_it_event.Service.HouseService;
import com.Sit_Perahat.sit_it_event.Service.UsersService;
import com.Sit_Perahat.sit_it_event.dto.DefaultResponse;
import com.Sit_Perahat.sit_it_event.dto.ScoreRequest;
import com.Sit_Perahat.sit_it_event.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final jwtProvider jwtProvider;
    private final HouseService houseService;

    @GetMapping
    public ResponseEntity<DefaultResponse> getUsers(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        String studentId = jwtProvider.getUserNameFromAuthentication(authentication);
        try {
            LocalDate allowedDate = LocalDate.of(2025, 8, 7);
            LocalDate today = LocalDate.now();
            Users user = usersService.findUser(studentId);
//
//            if (today.equals(allowedDate)) {
//                response.put("user", user);
//                return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse("success", "Retrieve Users By Id " + studentId, response));
//            }
            response.put("user", user);
            return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse("success", "Retrieve Users By Id " + studentId, response));
        } catch (RuntimeException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("RuntimeException", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse("error", "Not found Users By id " + studentId, errors));
        }

    }

    @PutMapping
    public ResponseEntity<DefaultResponse> updateUsers(Authentication authentication, @RequestBody ScoreRequest scoreRequest) {
        Map<String, Object> response = new HashMap<>();
        double score = scoreRequest.getScore();
        String studentId = jwtProvider.getUserNameFromAuthentication(authentication);

        Random random = new Random();
        try {
            Users user = usersService.findUser(studentId);
            int[] houseIds = score <= 14.29 ? new int[]{5,4,1} : new int[]{2,3,6};
            int randomIndex = random.nextInt(houseIds.length);
            Houses houses = houseService.getHouseById(houseIds[randomIndex]).orElseThrow(RuntimeException::new);
            Users updatedUser = usersService.UpdateUser(houses, user);
            return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse("success", "Update House Users By Id " + studentId, null));
        } catch (RuntimeException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("RuntimeException", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse("error", "Not found Users By id " + studentId, errors));
        }
    }
}
