package com.finalproject.rentacar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationResponse {
    private Long id;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Long userId;
    private Long carId;
    private Long daysBooked;
    private Double totalPrice;
}
