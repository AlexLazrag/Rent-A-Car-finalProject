package com.finalproject.rentacar.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String brand;
    private String model;
    private int seatsNumber;
    private double pricePerDay;

    @OneToOne(mappedBy = "car")
    @JsonBackReference
    private Set<Reservation> reservations;

}
