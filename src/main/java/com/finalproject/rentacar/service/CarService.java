package com.finalproject.rentacar.service;

import com.finalproject.rentacar.dto.CarRequest;
import com.finalproject.rentacar.dto.CarResponse;
import com.finalproject.rentacar.dto.UpdateCarRequest;
import org.springframework.stereotype.Service;

@Service
public interface CarService {
    CarResponse saveCar(CarRequest request);
    CarResponse getCar(Long id);
    void deleteCar(Long id);
    CarResponse updateCarDetails(Long id, UpdateCarRequest request);
}
