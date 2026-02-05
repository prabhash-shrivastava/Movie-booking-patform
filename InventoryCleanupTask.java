package com.platform.movie.booking.scheduler;

import com.platform.movie.booking.entity.SeatInventory;
import com.platform.movie.booking.repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@EnableScheduling
public class InventoryCleanupTask {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final String AVAILABLE_LABEL = "AVAILABLE";
    private static final String LOCKED_LABEL = "LOCKED";

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cleanupExpiredLocks() {
        List<SeatInventory> lockedSeats = inventoryRepository.findAllByStatus(LOCKED_LABEL);
        for (SeatInventory seat : lockedSeats) {
            String lockKey = "lock:show:" + seat.getShowId() + ":seat:" + seat.getSeatId();
            if (Boolean.FALSE.equals(redisTemplate.hasKey(lockKey))) {
                inventoryRepository.updateStatus(seat.getShowId(), seat.getSeatId(), AVAILABLE_LABEL );
            }
        }
    }
}
