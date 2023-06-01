package com.hospital.booking.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Bill extends BaseModel {
    private int id;
    private double price;
    private String patientName;
    private String patientPhoneNumber;
    private String patientEmail;
    private LocalDateTime checkoutAt;

    public Bill() {
        super();
    }

    public Bill(int id, double price, String patientName, String patientPhoneNumber, String patientEmail, LocalDateTime checkoutAt) {
        super();
        this.id = id;
        this.price = price;
        this.patientName = patientName;
        this.patientPhoneNumber = patientPhoneNumber;
        this.patientEmail = patientEmail;
        this.checkoutAt = checkoutAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public LocalDateTime getCheckoutAt() {
        return checkoutAt;
    }

    public void setCheckoutAt(LocalDateTime checkoutAt) {
        this.checkoutAt = checkoutAt;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", price=" + price +
                ", patientName='" + patientName + '\'' +
                ", patientPhoneNumber='" + patientPhoneNumber + '\'' +
                ", patientEmail='" + patientEmail + '\'' +
                ", checkoutAt=" + checkoutAt +
                '}';
    }
}
