package com.booking.platform.repository;

import com.booking.platform.entity.ShowSeatInventory;
import com.booking.platform.enums.SeatAvailabilityStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShowSeatInventoryRepository extends JpaRepository<ShowSeatInventory, Long> {

    List<ShowSeatInventory> findByShowId(Long showId);

    List<ShowSeatInventory> findByShowIdAndStatus(Long showId, SeatAvailabilityStatus status);

    Optional<ShowSeatInventory> findByShowIdAndSeatSeatNumber(Long showId, String seatNumber);

    List<ShowSeatInventory> findByShowIdAndSeatSeatNumberIn(Long showId, List<String> seatNumbers);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ShowSeatInventory s WHERE s.show.id = :showId AND s.seat.seatNumber IN :seatNumbers")
    List<ShowSeatInventory> findByShowIdAndSeatNumbersForUpdate(@Param("showId") Long showId,
                                                                @Param("seatNumbers") List<String> seatNumbers);
}