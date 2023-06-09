package com.hospital.booking.database;

public class ReviewQuery {
    private Integer id;
    private Integer doctorId;
    private Integer reviewerId;
    private Boolean reviewDoctor;

    public ReviewQuery() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Boolean getReviewDoctor() {
        return reviewDoctor;
    }

    public void setReviewDoctor(Boolean reviewDoctor) {
        this.reviewDoctor = reviewDoctor;
    }

    @Override
    public String toString() {
        return "ReviewQuery{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", reviewerId=" + reviewerId +
                ", reviewDoctor=" + reviewDoctor +
                '}';
    }
}
