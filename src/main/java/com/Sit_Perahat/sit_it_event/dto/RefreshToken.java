package com.Sit_Perahat.sit_it_event.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshToken {
    @NotNull(message = "refresh_token cannot null")
    private String refresh_token;
}
