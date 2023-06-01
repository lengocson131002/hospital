package com.hospital.booking.models;

import java.time.LocalDate;

public class Shift extends BaseModel {
    private int id;
    private Account doctor;
    private int slot;
    private int room;
    private LocalDate date;

    public Shift() {
        super();
    }

    public Shift(int id, Account doctor, int slot, int room, LocalDate date) {
        super();
        this.id = id;
        this.doctor = doctor;
        this.slot = slot;
        this.room = room;
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

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", slot=" + slot +
                ", room=" + room +
                ", date=" + date +
                '}';
    }
}
