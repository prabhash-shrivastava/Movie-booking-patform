package com.platform.movie.booking.dto;

public class SeatDTO {

    private String seatId;
    private Long showId;
    private String rowName;
    private int seatNumber;
    private boolean isAvailable;
    private String status;

    public SeatDTO() {}

    public SeatDTO(String seatId, Long showId, String rowName, int seatNumber, String status) {
        this.seatId = seatId;
        this.showId = showId;
        this.rowName = rowName;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public String getSeatId() { return seatId; }
    public void setSeatId(String seatId) { this.seatId = seatId; }
    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
    public String getRowName() { return rowName; }
    public void setRowName(String rowName) { this.rowName = rowName; }
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
