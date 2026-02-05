package com.platform.movie.booking.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SeatInventory {

    @Id
    private String seatId;
    private Long showId;
    private String status;
    private String rowName;
    private int seatNumber;

    public String getSeatId() { return seatId; }
    public void setSeatId(String seatId) { this.seatId = seatId; }
    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRowName() { return rowName; }
    public void setRowName(String rowName) { this.rowName = rowName; }
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
}
