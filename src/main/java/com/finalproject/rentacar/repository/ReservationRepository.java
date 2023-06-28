package com.finalproject.rentacar.repository;

import com.finalproject.rentacar.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByUserId(Long id);

    Set<Reservation> findByCarId(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM reservations " +
            "WHERE date_start BETWEEN :dateStart AND :dateEnd " +
            "OR " +
            "date_end BETWEEN :dateStart AND :dateEnd")
    Optional<Set<Reservation>> getReservationByIntervalWithNative(LocalDate dateStart, LocalDate dateEnd);
}
