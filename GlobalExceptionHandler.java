package com.platform.movie.booking.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.platform.movie.booking.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String SEAT_LOCKED_LABEL = "SEAT_LOCKED";
    private static final String NOT_FOUND_LABEL = "NOT_FOUND";
    private static final String INTERNAL_ERROR_LABEL = "INTERNAL_ERROR";

    @ExceptionHandler(ConcurrencyException.class)
    public ResponseEntity<ErrorResponse> handleConcurrency(ConcurrencyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(SEAT_LOCKED_LABEL, ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(NOT_FOUND_LABEL, ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(INTERNAL_ERROR_LABEL, "An unexpected error occurred."));
    }
}
