package com.finalproject.rentacar.controller;

import com.finalproject.rentacar.dto.ReservationRequest;
import com.finalproject.rentacar.dto.ReservationResponse;
import com.finalproject.rentacar.entity.Reservation;
import com.finalproject.rentacar.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentacar/v1/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping(path = "/create")
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.bookReservation(reservationRequest));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(reservationService.findById(id));
    }

    @GetMapping(path = "/findByUID/{id}")
    public ResponseEntity<ReservationResponse> findByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(reservationService.findByUserId(id));
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return HttpStatus.ACCEPTED;
    }
}
