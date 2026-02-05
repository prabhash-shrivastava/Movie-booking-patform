package com.platform.movie.booking.controller;

import com.platform.movie.booking.entity.SeatInventory;
import com.platform.movie.booking.repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class PartnerController {
    @Autowired
    private InventoryRepository inventoryRepository;
    @PostMapping("/inventory/init")
    public ResponseEntity<String> initializeInventory(@RequestBody List<SeatInventory> newSeats) {
        inventoryRepository.saveAll(newSeats);
        return ResponseEntity.ok("Inventory initialized successfully");
    }
}
