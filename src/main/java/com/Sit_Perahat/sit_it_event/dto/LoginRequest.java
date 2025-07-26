package com.Sit_Perahat.sit_it_event.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "studentId cannot null")
    @NotBlank(message = "studentId cannot blank")
    private String studentId;

    @NotNull(message = "password cannot null")
    @NotBlank(message = "password cannot blank")
    private String password;
}
