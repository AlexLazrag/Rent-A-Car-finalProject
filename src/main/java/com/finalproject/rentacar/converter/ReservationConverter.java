package com.finalproject.rentacar.converter;


import com.finalproject.rentacar.dto.ReservationRequest;
import com.finalproject.rentacar.dto.ReservationResponse;
import com.finalproject.rentacar.entity.Car;
import com.finalproject.rentacar.entity.Reservation;
import com.finalproject.rentacar.entity.User;
import com.finalproject.rentacar.exceptions.NotFoundException;
import com.finalproject.rentacar.repository.CarRepository;
import com.finalproject.rentacar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
public class ReservationConverter {
    @Autowired
    CarRepository carRepository;
    @Autowired
    UserRepository userRepository;


    public Reservation toReservation(ReservationRequest request) {
        Reservation reservation = Reservation.builder()
                .user(getUser(request.getUserId()))
                .car(getCar(request.getCarId()))
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .daysBooked(getDaysBooked(request))
                .totalPrice(getPrice(request.getCarId()) * getDaysBooked(request))
                .build();
        return reservation;
    }

    public ReservationResponse toResponse(Reservation savedReservation) {
        return new ReservationResponse(savedReservation.getId(),
                savedReservation.getDateStart(),
                savedReservation.getDateEnd(),
                savedReservation.getUser().getId(),
                savedReservation.getCar().getId(),
                savedReservation.getDaysBooked(),
                savedReservation.getTotalPrice());
    }

    Long getDaysBooked(ReservationRequest request) {
        return ChronoUnit.DAYS.between(request.getDateStart(), request.getDateEnd());
    }

    User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "Cannot proceed with reservation. User with " + id + " not found"
        ));
    }

    Car getCar(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "Cannot proceed with reservation. Car with " + id + " not found"
        ));
    }

    Double getPrice(long id) {
        return carRepository.findById(id).get().getPricePerDay();
    }

}
