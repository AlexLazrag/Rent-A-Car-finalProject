package com.finalproject.rentacar.service.impl;

import com.finalproject.rentacar.converter.ReservationConverter;
import com.finalproject.rentacar.dto.ReservationRequest;
import com.finalproject.rentacar.dto.ReservationResponse;
import com.finalproject.rentacar.dto.UpdateReservationCarRequest;
import com.finalproject.rentacar.dto.UpdateReservationDatesRequest;
import com.finalproject.rentacar.entity.Car;
import com.finalproject.rentacar.entity.Reservation;

import com.finalproject.rentacar.exceptions.DuplicateEntityException;
import com.finalproject.rentacar.exceptions.NotFoundException;
import com.finalproject.rentacar.repository.CarRepository;
import com.finalproject.rentacar.repository.ReservationRepository;

import com.finalproject.rentacar.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationConverter reservationConverter;
    private final CarRepository carRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationConverter reservationConverter,
                                  CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationConverter = reservationConverter;
        this.carRepository = carRepository;
    }

    @Override
    public ReservationResponse bookReservation(ReservationRequest request) {
        Reservation reservation = reservationConverter.toReservation(request);
        Reservation existingUID = reservationRepository.findByUserId(request.getUserId());

        //Прави се проверка дали е направена резервация за с това userId, за да не се инкрементира reservationId ненужно.
        if (existingUID != null){
            throw new DuplicateEntityException("User with id '" + existingUID.getUser().getId() +
                    "' has already reserved car with id '" + existingUID.getCar().getId() + "'");

        }

        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationConverter.toResponse(savedReservation);
    }

    @Override
    public ReservationResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Reservation not found"));
        return reservationConverter.toResponse(reservation);
    }

    @Override
    public ReservationResponse getByUserId(Long id) {
        Reservation reservation = reservationRepository.findByUserId(id);
        return reservationConverter.toResponse(reservation);
    }

    @Override
    public Set<Reservation> getByCarId(Long id) {
        return reservationRepository.findByCarId(id);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public Set<Reservation> findReservationByPeriod(LocalDate dateStart, LocalDate dateEnd) {
        return reservationRepository.getReservationByIntervalWithNative(dateStart, dateEnd).orElse(Collections.emptySet());

    }

    @Override
    public ReservationResponse changeCar(Long id, UpdateReservationCarRequest request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        Car car = carRepository.findById(request.getCarId()).orElseThrow();

        if (request.getCarId() !=null && reservation.getCar().getId() != request.getCarId()){
            reservation.setCar(car);
        }
        return reservationConverter.toResponse(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponse changeDates(Long id, UpdateReservationDatesRequest request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        reservation.setDateStart(request.getDateStart());
        reservation.setDateEnd(request.getDateEnd());
        return reservationConverter.toResponse(reservationRepository.save(reservation));
    }
}
