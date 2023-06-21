package com.finalproject.rentacar.repository;

import com.finalproject.rentacar.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Reservation findByUserId(Long id);
    Reservation findByCarId(Long id);
}
