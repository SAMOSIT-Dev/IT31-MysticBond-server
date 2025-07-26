package com.Sit_Perahat.sit_it_event.Controller;


import com.Sit_Perahat.sit_it_event.Entity.Hints;
import com.Sit_Perahat.sit_it_event.Service.HintService;
import com.Sit_Perahat.sit_it_event.dto.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hints")
public class HintController {

    private final HintService hintService;

    // รองานว่าจะเอาเป็นยังไง ระหว่าง ให้ใส่รหัสของน้อง หรือ เข้ามา ดู
    @GetMapping("/{studentId}")
    public ResponseEntity<DefaultResponse> findHints(@PathVariable String studentId) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<String> allHints = hintService.findHints(studentId).stream().map(Hints::getMessage).toList();
            response.put("hints", allHints);
            return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse("success", "Retrieve all hint by Id " + studentId,response ));

        } catch (RuntimeException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("RuntimeException", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse("error", "Not found Hint By id " + studentId, errors));
        }
    }


}
