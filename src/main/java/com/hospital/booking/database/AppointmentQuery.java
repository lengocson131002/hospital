package com.hospital.booking.database;

import com.hospital.booking.enums.AppointmentStatus;

import java.time.LocalDate;

public class AppointmentQuery {
    private Integer doctorId;
    private Integer bookerId;
    private LocalDate from;
    private LocalDate to;
    private Integer id;
    private Integer billId;
    private Integer Slot;
    private AppointmentStatus status;
    private String query;

    public AppointmentQuery() {
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getBookerId() {
        return bookerId;
    }

    public void setBookerId(Integer bookerId) {
        this.bookerId = bookerId;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getSlot() {
        return Slot;
    }

    public void setSlot(Integer slot) {
        Slot = slot;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
