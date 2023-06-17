package com.finalproject.rentacar.controller;

import com.finalproject.rentacar.dto.CarRequest;
import com.finalproject.rentacar.dto.CarResponse;
import com.finalproject.rentacar.dto.UpdateCarRequest;
import com.finalproject.rentacar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rentacar/v1/car")
public class CarController {
    @Autowired
    CarService carService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addCar(@RequestBody CarRequest request) {
        CarResponse carResponse = carService.saveCar(request);
        String response = String.format("Car:%n %s%n %s%n was created with id %s",
                carResponse.getBrand(), carResponse.getModel(), carResponse.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CarResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(carService.getCar(id));
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return HttpStatus.ACCEPTED;
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @RequestBody UpdateCarRequest request) {
        CarResponse carResponse = carService.updateCarDetails(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }
}
