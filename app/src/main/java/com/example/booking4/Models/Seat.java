package com.example.booking4.Models;

public class Seat {
    private SeatStatus Status;
    private String Name;

    public Seat(SeatStatus Status, String Name) {
        this.Status = Status;
        this.Name = Name;
    }

    public SeatStatus getStatus() {
        return Status;
    }

    public void setStatus(SeatStatus Status) {
        this.Status = Status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public enum SeatStatus {
        AVAILABLE, SELECTED, UNAVAILABLE
    }
}
