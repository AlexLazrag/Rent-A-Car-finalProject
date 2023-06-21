package com.finalproject.rentacar.service;

import com.finalproject.rentacar.dto.ReservationRequest;
import com.finalproject.rentacar.dto.ReservationResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {
    ReservationResponse bookReservation (ReservationRequest request);
    ReservationResponse findById(Long id);
    ReservationResponse getByUserId(Long id);
    ReservationResponse getByCarId(Long id);
    void deleteReservation(Long id);

}
