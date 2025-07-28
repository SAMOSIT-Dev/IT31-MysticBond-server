package com.Sit_Perahat.sit_it_event.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Users {

    @Id
    private String studentId;

    private String nickname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id",referencedColumnName = "id")
    private Houses house;

    @Column(name = "is_answered", nullable = false)
    private Boolean isAnswered;
}
