package com.finalproject.rentacar.service.impl;

import com.finalproject.rentacar.converter.ReservationConverter;
import com.finalproject.rentacar.dto.ReservationRequest;
import com.finalproject.rentacar.dto.ReservationResponse;
import com.finalproject.rentacar.entity.Reservation;
import com.finalproject.rentacar.entity.User;
import com.finalproject.rentacar.exceptions.NotFoundException;
import com.finalproject.rentacar.repository.ReservationRepository;
import com.finalproject.rentacar.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationConverter reservationConverter;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationConverter reservationConverter) {
        this.reservationRepository = reservationRepository;
        this.reservationConverter = reservationConverter;
    }

    @Override
    public ReservationResponse bookReservation(ReservationRequest request) {
        //TODO FIX CONNECTIONS
        Reservation reservation = reservationConverter.toReservation(request);
        Reservation savedReservation = reservationRepository.save(reservation);
//        User user = savedReservation.getUser();
//        user.setReservation(savedReservation);


        return reservationConverter.toResponse(savedReservation);
    }

    @Override
    public ReservationResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Reservation not found"));
        return reservationConverter.toResponse(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
