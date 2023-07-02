package com.finalproject.rentacar.service;

import com.finalproject.rentacar.dto.ReservationRequest;
import com.finalproject.rentacar.dto.ReservationResponse;
import com.finalproject.rentacar.dto.UpdateReservationCarRequest;
import com.finalproject.rentacar.dto.UpdateReservationDatesRequest;
import com.finalproject.rentacar.entity.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public interface ReservationService {
    ReservationResponse bookReservation (ReservationRequest request);
    ReservationResponse findById(Long id);
    ReservationResponse getByUserId(Long id);
    Set<Reservation> getByCarId(Long id);
    void deleteReservation(Long id);

    Set<Reservation> findReservationByPeriod(LocalDate dateStart, LocalDate dateEnd);

    ReservationResponse changeCar(Long id , UpdateReservationCarRequest request);
    ReservationResponse changeDates(Long id, UpdateReservationDatesRequest request);
}
