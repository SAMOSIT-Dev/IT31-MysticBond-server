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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.math.BigDecimal;
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
             ZoneId thailandZoneId = ZoneId.of("Asia/Bangkok");
             LocalDate today = ZonedDateTime.now(thailandZoneId).toLocalDate();
             LocalDate allowedDate = LocalDate.of(2025, 8, 7);
            Users user = usersService.findUser(studentId);
             if (!today.isBefore(allowedDate)) {
                response.put("user", user);
                return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse("success", "Retrieve Users By Id " + studentId, response));
             }
             response.put("message", "ยังไม่ถึงเวลารู้บ้านครับน้อง");
             UserResponse userResponse = new UserResponse();
             userResponse.setStudentId(studentId);
             userResponse.setNickname(user.getNickname());
             userResponse.setIsAnswered(user.getIsAnswered());
             response.put("user", userResponse);
             return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse("success", "Retrieve Users By Id " + studentId, response));
        } catch (RuntimeException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("RuntimeException", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse("error", "Not found Users By id " + studentId, errors));
        }

    }

    @PutMapping
    public ResponseEntity<DefaultResponse> updateUsers(Authentication authentication, @RequestBody ScoreRequest data) {
        Map<String, Object> response = new HashMap<>();
        String studentId = jwtProvider.getUserNameFromAuthentication(authentication);
        Map<String, String> colorToHouse = Map.of(
                "R", "Phoenix",
                "O", "Griffin",
                "Y", "Qilin",
                "G", "Basilisk",
                "B", "Leviathan",
                "P", "Kitsune"
        );
        try {
            Users user = usersService.findUser(studentId);
            Map.Entry<String, BigDecimal> maxEntry = data.getData().entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .orElseThrow(RuntimeException::new);
            String house = colorToHouse.get(maxEntry.getKey());
            Houses houses = houseService.getHousesByName(house);
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
