package com.finalproject.rentacar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarRequest {
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @NotNull
    private int seats;
    @NotNull
    private Double pricePerDay;
}
