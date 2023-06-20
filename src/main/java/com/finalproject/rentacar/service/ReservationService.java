package com.finalproject.rentacar.service;

import com.finalproject.rentacar.dto.ReservationRequest;
import com.finalproject.rentacar.dto.ReservationResponse;
import com.finalproject.rentacar.entity.Reservation;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {
    Reservation bookReservation (ReservationRequest request);
    ReservationResponse findById(Long id);
    void deleteReservation(Long id);

}
