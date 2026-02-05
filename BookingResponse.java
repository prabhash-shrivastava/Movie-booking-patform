package com.platform.movie.booking.entity;

public class BookingResponse {

    private String status;
    private String message;
    private Long bookingId;
    private double finalAmount;

    public BookingResponse(String status, String message, Long bookingId, double finalAmount)
    {
        this.status = status;
        this.message = message;
        this.bookingId = bookingId;
        this.finalAmount = finalAmount;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
}
