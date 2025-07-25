package com.Sit_Perahat.sit_it_event.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse {
    private String status;
    private String message;
    private Map<String, Object> data;

}
