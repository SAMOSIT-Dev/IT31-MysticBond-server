package com.Sit_Perahat.sit_it_event.Controller;


import com.Sit_Perahat.sit_it_event.Entity.Houses;
import com.Sit_Perahat.sit_it_event.Service.HouseService;
import com.Sit_Perahat.sit_it_event.dto.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/houses")
public class HouseController {


    @Autowired
    private HouseService houseService;

    @GetMapping
    private ResponseEntity<DefaultResponse> getAllHouses() {
        Map<String, Object> responseHouse = new HashMap<>();
        List<String> allHouseNames = houseService.getAllHouses()
                .stream()
                .map(Houses::getHouseName)
                .collect(Collectors.toList());

        DefaultResponse defaultResponse = new DefaultResponse();

        if (allHouseNames.isEmpty()) {
            defaultResponse.setData(null);
            defaultResponse.setMessage("No houses in database");
            defaultResponse.setStatus("success");
            return ResponseEntity.status(HttpStatus.OK).body(defaultResponse);
        }

        responseHouse.put("houseName", allHouseNames);
        defaultResponse.setStatus("success");
        defaultResponse.setMessage("Retrieve all houses");
        defaultResponse.setData(responseHouse);
        return ResponseEntity.ok(defaultResponse);
    }

    @GetMapping("/{id}")
    private ResponseEntity<DefaultResponse> getHouseById(@PathVariable String id) {
        DefaultResponse defaultResponse = new DefaultResponse();
        Optional<Houses> houseOptional = houseService.getHouseById(Integer.parseInt(id));

        if (houseOptional.isPresent()) {
            defaultResponse.setStatus("success");
            defaultResponse.setMessage("Retrieve house by ID");

            Map<String,Object> response = new HashMap<>();
            response.put("houseName", houseOptional.get().getHouseName());
            defaultResponse.setData(response);

            return ResponseEntity.ok(defaultResponse);
        }

        defaultResponse.setStatus("error");
        defaultResponse.setMessage("No house with ID " + id + " in database");
        defaultResponse.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(defaultResponse);
    }

}
