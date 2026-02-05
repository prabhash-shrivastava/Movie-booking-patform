package com.platform.movie.booking.service;

import com.platform.movie.booking.dto.SeatDTO;
import com.platform.movie.booking.entity.Booking;
import com.platform.movie.booking.entity.BookingRequest;
import com.platform.movie.booking.entity.BookingResponse;
import com.platform.movie.booking.entity.SeatInventory;
import com.platform.movie.booking.repo.BookingRepository;
import com.platform.movie.booking.repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private DiscountConfigProvider configProvider;

    private static final String AVAILABLE_LABEL ="AVAILABLE";
    private static final String LOCKED_LABEL ="LOCKED";
    private static final String PENDING_LABEL ="PENDING";
    private static final String SUCCESS_LABEL ="SUCCESS";

    public List<SeatDTO> getSeatMap(Long showId) {
        List<SeatInventory> seats = inventoryRepository.findAllByShowId(showId);
        return seats.stream().map(this::convertToDTO).toList();
    }

    private SeatDTO convertToDTO(SeatInventory entity) {
        SeatDTO dto = new SeatDTO(entity.getSeatId(), entity.getShowId(), entity.getRowName(),
                entity.getSeatNumber(), entity.getStatus());
        String lockKey = "lock:show:" + entity.getShowId() + ":seat:" + entity.getSeatId();
        boolean isLockedInRedis = redisTemplate.hasKey(lockKey);
        dto.setAvailable(AVAILABLE_LABEL.equals(entity.getStatus()) && !isLockedInRedis);
        return dto;
    }

    @Transactional
    public BookingResponse reserveSeats(BookingRequest request) {
        String lockKey = "lock:show:" + request.getShowId() + ":seat:" + request.getSeatId();
        Boolean acquired = redisTemplate.opsForValue().setIfAbsent(lockKey,
                request.getUserId().toString(), Duration.ofMinutes(5));
        if (Boolean.FALSE.equals(acquired)) throw new RuntimeException("Seat currently held by another user");
        try {
            SeatInventory seat =
                    inventoryRepository.findByShowIdAndSeatId(request.getShowId(), request.getSeatId())

                            .orElseThrow(() -> new RuntimeException("Seat not found"));
            if (!AVAILABLE_LABEL.equals(seat.getStatus())) throw new RuntimeException("Seat no longer available");
            double discount = 0.0;
            if (request.getTicketCount() >= configProvider.getTicketThreshold()) {

                discount = (request.getBasePrice() * request.getTicketCount()) *
                        (configProvider.getCurrentDiscountPct() / 100);
            }
            double finalPrice = (request.getBasePrice() * request.getTicketCount()) - discount;
            inventoryRepository.updateStatus(request.getShowId(), request.getSeatId(),
                    LOCKED_LABEL);
            Booking booking = bookingRepository.save(new Booking(request.getUserId(),
                    request.getShowId(), finalPrice, PENDING_LABEL));
            return new BookingResponse(SUCCESS_LABEL, "Seat held for 5 minutes",
                    booking.getId(), finalPrice);
        } catch (Exception e) {
            redisTemplate.delete(lockKey);
            throw e;
        }
    }
}
