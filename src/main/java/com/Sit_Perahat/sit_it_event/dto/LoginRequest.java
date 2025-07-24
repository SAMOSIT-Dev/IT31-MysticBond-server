package com.Sit_Perahat.sit_it_event.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "username cannot null")
    @NotBlank(message = "username cannot blank")
    private String username;

    @NotNull(message = "password cannot null")
    @NotBlank(message = "password cannot blank")
    private String password;
}
