package com.platform.movie.booking.repo;

import com.platform.movie.booking.entity.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<SeatInventory, String> {
        List<SeatInventory> findAllByShowId(Long showId);

        Optional<SeatInventory> findByShowIdAndSeatId(Long showId, String seatId);

        @Modifying
        @Query("UPDATE SeatInventory s SET s.status = :status WHERE s.showId = :showId AND s.seatId = :seatId")
        void updateStatus(Long showId, String seatId, String status);

        List<SeatInventory> findAllByStatus(String lockedLabel);
}
