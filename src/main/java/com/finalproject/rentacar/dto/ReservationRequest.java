package com.finalproject.rentacar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationRequest {
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;
    private Long userId;
    private Long carId;

}
