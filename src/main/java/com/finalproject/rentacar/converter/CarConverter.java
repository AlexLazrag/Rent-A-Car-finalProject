package com.finalproject.rentacar.converter;

import com.finalproject.rentacar.dto.CarRequest;
import com.finalproject.rentacar.dto.CarResponse;
import com.finalproject.rentacar.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarConverter {

    public Car toCar(CarRequest request){
        return Car.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .seats(request.getSeats())
                .pricePerDay(request.getPricePerDay())
                .build();
    }

    public CarResponse toResponse(Car savedCar){
        return new CarResponse(
                savedCar.getId(),
                savedCar.getBrand(),
                savedCar.getModel(),
                savedCar.getSeats(),
                savedCar.getPricePerDay());
    }
}
