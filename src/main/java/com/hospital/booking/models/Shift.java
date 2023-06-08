package com.hospital.booking.models;


import java.time.LocalDate;

public class Shift extends BaseModel {
    private int id;
    private Account doctor;
    private int slot;

    private LocalDate date;

    private boolean booked;

    private Slot slotInfo;

    public Shift() {
        super();
    }

    public Shift(int id, Account doctor, LocalDate date, int slot) {
        this.doctor = doctor;
        this.slot = slot;
        this.date = date;
    }

    public Shift(Account doctor, LocalDate date, int slot) {
        this.doctor = doctor;
        this.slot = slot;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getDoctor() {
        return doctor;
    }

    public void setDoctor(Account doctor) {
        this.doctor = doctor;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Slot getSlotInfo() {
        return slotInfo;
    }

    public void setSlotInfo(Slot slotInfo) {
        this.slotInfo = slotInfo;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", slot=" + slot +
                ", day=" + date +
                ", booked=" + booked +
                '}';
    }
}
