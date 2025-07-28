package com.Sit_Perahat.sit_it_event.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    private String studentId;
    private String nickname;
    private Boolean isAnswered;
}
