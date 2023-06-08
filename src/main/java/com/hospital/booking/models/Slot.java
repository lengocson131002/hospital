package com.hospital.booking.models;

import java.util.Calendar;

public class Slot {
    private String startTime;

    private Calendar startTimeCalendar;

    private String endTime;

    private Calendar endTimeCalendar;

    private int number;

    private SlotStatus status;

    public Slot() {
    }

    public Slot(String startTime, String endTime, int slotNumber) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.number = slotNumber;
    }

    public Slot(String startTime, String endTime, int number, SlotStatus status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.number = number;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", slotNumber=" + number +
                ", status=" + status +
                '}';
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

    public Calendar getStartTimeCalendar() {
        return startTimeCalendar;
    }

    public void setStartTimeCalendar(Calendar startTimeCalendar) {
        this.startTimeCalendar = startTimeCalendar;
    }

    public Calendar getEndTimeCalendar() {
        return endTimeCalendar;
    }

    public void setEndTimeCalendar(Calendar endTimeCalendar) {
        this.endTimeCalendar = endTimeCalendar;
    }

    public static enum SlotStatus {
        FREE, SHIFTED, BOOKED
    }
}
