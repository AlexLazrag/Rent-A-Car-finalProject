package com.finalproject.rentacar.controller;

import com.finalproject.rentacar.converter.ReservationConverter;
import com.finalproject.rentacar.dto.ReservationRequest;
import com.finalproject.rentacar.dto.ReservationResponse;
import com.finalproject.rentacar.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/rentacar/v1/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationConverter reservationConverter;

    @PostMapping(path = "/create")
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.bookReservation(reservationRequest));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(reservationService.findById(id));
    }

    @GetMapping(path = "/findByUID")
    public ResponseEntity<ReservationResponse> findByUserId(@RequestParam("id") Long id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(reservationService.getByUserId(id));
    }

    @GetMapping(path = "/findByCID")
    public ResponseEntity<ReservationResponse> findByCarId(@RequestParam("id") Long id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(reservationService.getByCarId(id));
    }

    @GetMapping(path = "/period")
    public ResponseEntity<Set<ReservationResponse>> getByPeriod(@RequestParam("dateStart") LocalDate dateStart,
                                                                @RequestParam("dateEnd") LocalDate dateEnd) {

        Set<ReservationResponse> reservationResponses = new HashSet<>();
        reservationService.findReservationByPeriod(dateStart, dateEnd).forEach(
                reservation -> {
                    ReservationResponse reservationResponse = reservationConverter.toResponse(reservation);
                    reservationResponses.add(reservationResponse);
                }
        );
        return ResponseEntity.status(HttpStatus.FOUND).body(reservationResponses);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return HttpStatus.ACCEPTED;
    }
}
