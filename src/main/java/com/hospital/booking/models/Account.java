package com.hospital.booking.models;

import com.hospital.booking.enums.Gender;
import com.hospital.booking.enums.Role;

import java.time.LocalDate;

public class Account extends BaseModel implements Cloneable{
    private int id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String avatar;
    private String phoneNumber;
    private String email;
    private String password;
    private Department department;
    private LocalDate DOB;
    private String description;
    private String token;
    private Role role;
    private String address;

    private boolean isActive;

    private Float score;

    private int appointmentCount;

    public Account() {
        isActive = true;
    }

    public Account(int id, String firstName, String lastName, Gender gender, String avatar, String phoneNumber, String email, Department department, LocalDate DOB, String description, String token, Role role) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.department = department;
        this.DOB = DOB;
        this.description = description;
        this.token = token;
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public int getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(int appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", department=" + department +
                ", DOB=" + DOB +
                ", description='" + description + '\'' +
                ", token='" + token + '\'' +
                ", role=" + role +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                ", score=" + score +
                '}';
    }
}
