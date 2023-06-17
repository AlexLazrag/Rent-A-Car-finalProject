package com.finalproject.rentacar.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 30)
    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private Date registerDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Reservation> reservations;




}
