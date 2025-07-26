package com.Sit_Perahat.sit_it_event.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Hints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private Users user;
}
