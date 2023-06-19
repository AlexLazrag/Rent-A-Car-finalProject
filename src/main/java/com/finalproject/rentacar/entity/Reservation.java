package com.finalproject.rentacar.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date dateStart;
    private Date dateEnd;

    @OneToOne
//    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @OneToOne
    @JsonManagedReference
    private Car car;


}
