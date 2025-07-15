package com.Sit_Perahat.sit_it_event.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class DefaultResponse {
    private String status;
    private String message;
    private Map<String, String> errors;

}
