package com.Sit_Perahat.sit_it_event.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ScoreRequest {

    private Map<String, BigDecimal> data;
}
