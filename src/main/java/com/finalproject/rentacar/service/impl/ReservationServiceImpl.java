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
import java.util.List;
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
        Reservation existingUserId = reservationRepository.findByUserId(request.getUserId());

        //Имплементираме Query метода за преплитане на дати на резервация
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                reservation.getCar().getId(),
                reservation.getDateStart(),
                reservation.getDateEnd()
        );

        //Прави се проверка дали е направена резервация за с това userId, за да не се инкрементира reservationId ненужно.
        if (existingUserId != null) {
            throw new DuplicateEntityException("User with id '" + existingUserId.getUser().getId() +
                    "' has already reserved car with id '" + existingUserId.getCar().getId() + "'");

        }
        //правим проверка ако колата, която се опитваме да резервираме е вече резервирана в този период от време
        if (!overlappingReservations.isEmpty()) {
            LocalDate conflictingStartDate = overlappingReservations.get(0).getDateStart();
            LocalDate conflictingEndDate = overlappingReservations.get(0).getDateEnd();


            throw new DuplicateEntityException("Car with id " + reservation.getCar().getId() + " is already reserved between "
                    + conflictingStartDate + " and " + conflictingEndDate);
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

        //Добавяме пак overlapping метода с цел да проверим дали колата, която искаме да update-нем в резервацията, вече не е резервирана
        // за датите зададени в резервацията.

        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                //използваме request вместо reservation, за да проверим колата, която искаме да сложим в съществуващата резервация
                request.getCarId(),
                reservation.getDateStart(),
                reservation.getDateEnd()
        );

        if (!overlappingReservations.isEmpty()){
            LocalDate conflictingStartDate = overlappingReservations.get(0).getDateStart();
            LocalDate conflictingEndDate = overlappingReservations.get(0).getDateEnd();


            throw new DuplicateEntityException("Car with id " + request.getCarId() + " is already reserved between "
                    + conflictingStartDate + " and " + conflictingEndDate);

        }

        if (request.getCarId() != null && reservation.getCar().getId() != request.getCarId()) {
            reservation.setCar(car);
        }
        return reservationConverter.toResponse(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponse changeDates(Long id, UpdateReservationDatesRequest request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        //Добавяме overlapping метода, за проверка на датите на резервацията с цел да не се преплитат датите на резервация за една и съща кола.
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                reservation.getCar().getId(),
                request.getDateStart(),
                request.getDateEnd()
        );

        if (!overlappingReservations.isEmpty()){
            LocalDate conflictingStartDate = overlappingReservations.get(0).getDateStart();
            LocalDate conflictingEndDate = overlappingReservations.get(0).getDateEnd();


            throw new DuplicateEntityException("Car with id " + reservation.getCar().getId() + " is already reserved between "
                    + conflictingStartDate + " and " + conflictingEndDate);

        }

        reservation.setDateStart(request.getDateStart());
        reservation.setDateEnd(request.getDateEnd());
        return reservationConverter.toResponse(reservationRepository.save(reservation));
    }
}
