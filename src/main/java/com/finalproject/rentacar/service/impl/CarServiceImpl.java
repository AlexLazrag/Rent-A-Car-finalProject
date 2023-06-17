package com.finalproject.rentacar.service.impl;

import com.finalproject.rentacar.converter.CarConverter;
import com.finalproject.rentacar.dto.CarRequest;
import com.finalproject.rentacar.dto.CarResponse;
import com.finalproject.rentacar.dto.UpdateCarRequest;
import com.finalproject.rentacar.entity.Car;
import com.finalproject.rentacar.exceptions.NotFoundException;
import com.finalproject.rentacar.repository.CarRepository;
import com.finalproject.rentacar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarConverter carConverter;
@Autowired
    public CarServiceImpl(CarRepository carRepository, CarConverter carConverter) {
        this.carRepository = carRepository;
        this.carConverter = carConverter;
    }

    @Override
    public CarResponse saveCar(CarRequest request) {
        Car car = carConverter.toCar(request);

        Car savedCar = carRepository.save(car);

        return carConverter.toResponse(savedCar);
    }

    @Override
    public CarResponse getCar(Long id) {
        Car car = carRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Car not found")
        );
        return carConverter.toResponse(car);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarResponse updateCarDetails(Long id, UpdateCarRequest request) {
    Car car = carRepository.findById(id).orElseThrow();

    if (request.getPricePerDay() != null){
        car.setPricePerDay(request.getPricePerDay());
    }
        return carConverter.toResponse(carRepository.save(car));
    }
}
