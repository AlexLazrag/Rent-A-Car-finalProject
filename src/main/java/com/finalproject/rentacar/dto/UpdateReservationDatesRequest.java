package com.finalproject.rentacar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReservationDatesRequest {
    private LocalDate dateStart;
    private LocalDate dateEnd;
}
