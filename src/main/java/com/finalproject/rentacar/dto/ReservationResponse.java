package com.finalproject.rentacar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    private Long daysBooked = ChronoUnit.DAYS.between(dateStart, dateEnd);
    private Double totalPrice;
}
