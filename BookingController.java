package com.platform.movie.booking.controller;


import com.platform.movie.booking.dto.SeatDTO;
import com.platform.movie.booking.entity.BookingRequest;
import com.platform.movie.booking.entity.BookingResponse;
import com.platform.movie.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @GetMapping("/shows/{showId}/seats")
    public ResponseEntity<List<SeatDTO>> getSeatMap(@PathVariable Long showId) {
        List<SeatDTO> seatMap = bookingService.getSeatMap(showId);
        return ResponseEntity.ok(seatMap);
    }

    @PostMapping("/reserve")
    public ResponseEntity<BookingResponse> reserveSeat(@RequestBody BookingRequest request) {
        if (request.getSeatId() == null || request.getShowId() == null) {
            return ResponseEntity.badRequest().build();
        }

        BookingResponse response = bookingService.reserveSeats(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
