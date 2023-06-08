package com.hospital.booking.models;

import com.hospital.booking.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class Appointment extends BaseModel {
    private int id;
    private Account booker;
    private Account doctor;
    private Shift shift;
    private Bill bill;
    private AppointmentStatus status = AppointmentStatus.CREATED;
    private String patientName;
    private String patientPhoneNumber;
    private String patientEmail;
    private String patientNote;
    private String doctorNote;
    private LocalDateTime canceledAt;
    private LocalDateTime finishedAt;

    public Appointment() {
        super();
    }

    public Appointment(int id, Account booker, Account doctor, Shift shift, Bill bill, AppointmentStatus status, String patientName, String patientPhoneNumber, String patientEmail, String patientNote, String doctorNote, LocalDateTime canceledAt, LocalDateTime finishedAt) {
        super();
        this.id = id;
        this.booker = booker;
        this.doctor = doctor;
        this.shift = shift;
        this.bill = bill;
        this.status = status;
        this.patientName = patientName;
        this.patientPhoneNumber = patientPhoneNumber;
        this.patientEmail = patientEmail;
        this.patientNote = patientNote;
        this.doctorNote = doctorNote;
        this.canceledAt = canceledAt;
        this.finishedAt = finishedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getBooker() {
        return booker;
    }

    public void setBooker(Account booker) {
        this.booker = booker;
    }

    public Account getDoctor() {
        return doctor;
    }

    public void setDoctor(Account doctor) {
        this.doctor = doctor;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
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

    public String getPatientNote() {
        return patientNote;
    }

    public void setPatientNote(String patientNote) {
        this.patientNote = patientNote;
    }

    public String getDoctorNote() {
        return doctorNote;
    }

    public void setDoctorNote(String doctorNote) {
        this.doctorNote = doctorNote;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(LocalDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", booker=" + booker +
                ", doctor=" + doctor +
                ", shift=" + shift +
                ", status=" + status +
                ", patientName='" + patientName + '\'' +
                ", patientPhoneNumber='" + patientPhoneNumber + '\'' +
                ", patientEmail='" + patientEmail + '\'' +
                ", patientNote='" + patientNote + '\'' +
                ", doctorNote='" + doctorNote + '\'' +
                ", canceledAt=" + canceledAt +
                ", finishedAt=" + finishedAt +
                '}';
    }
}
