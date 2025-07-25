package com.Sit_Perahat.sit_it_event.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Houses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "house_name")
    private String houseName;
}
