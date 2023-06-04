package com.hospital.booking.models;

import com.hospital.booking.enums.Day;

import java.time.LocalDate;

public class Shift extends BaseModel {
    private int id;
    private Account doctor;
    private int slot;
    private int room;
    private Day day;

    public Shift() {
        super();
    }

    public Shift(int id, Account doctor, int slot, int room, Day day) {
        super();
        this.id = id;
        this.doctor = doctor;
        this.slot = slot;
        this.room = room;
        this.day = day;
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

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", slot=" + slot +
                ", room=" + room +
                ", day=" + day.name() +
                '}';
    }
}
