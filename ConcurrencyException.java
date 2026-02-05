package com.platform.movie.booking.ExceptionHandler;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String message) {
        super(message);
    }
}
