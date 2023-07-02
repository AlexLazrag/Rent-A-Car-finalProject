package com.finalproject.rentacar.repository;

import com.finalproject.rentacar.entity.Reservation;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
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

    //Направих Query с цел да се провери от кога до кога е запазена една кола и да се провери дали при създаване на резервация
    //се препокриват датите на резервиране на колата.
    @Query(nativeQuery = true,
            value =
            "SELECT * FROM reservations " +
            "WHERE car_id = :carId " +
            "AND ((date_start <= :dateEnd) AND (date_end >= :dateStart))")
    List<Reservation> findOverlappingReservations(@Param("carId") Long carId,
                                                  @Param("dateStart") LocalDate dateStart,
                                                  @Param("dateEnd") LocalDate dateEnd);
}

