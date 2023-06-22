package com.hospital.booking.daos;

import com.hospital.booking.database.AccountQuery;
import com.hospital.booking.database.DatabaseConnection;
import com.hospital.booking.database.ReviewQuery;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Department;
import com.hospital.booking.models.Review;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewDao {

    public List<Review> getAll(ReviewQuery query) {
        if (query == null) {
            query = new ReviewQuery();
        }
        String sql =
                "select re.*, " +
                        "d.Id as DoctorId, " +
                        "d.LastName as DoctorLastName, " +
                        "d.FirstName as DoctorFirstName, " +
                        "d.Avatar as DoctorAvatar," +
                        "d.Email as DoctorEmail," +
                        "r.Id as ReviewerId, " +
                        "r.LastName as ReviewerLastName, " +
                        "r.FirstName as ReviewerFirstName, " +
                        "r.Avatar as ReviewerAvatar, " +
                        "r.Email as ReviewerEmail, " +
                        "ap.Id as AppointmentId " +
                        "from Review re " +
                        "   left join Account d on d.Id = re.DoctorId " +
                        "   left join Account r on r.Id = re.ReviewerId " +
                        "   left join Appointment ap on ap.Id = re.AppointmentId " +
                        "   where (? is null or re.Id = ?) " +
                        "   and (? is null or re.DoctorId = ?) " +
                        "   and (? is null or re.ReviewerId = ?) " +
                        "   and (? is null or (? = 1 and re.DoctorId is not null) or (? = 0 and re.DoctorId is null)) " +
                        "   and (? is null or ap.Id = ?) " +
                        "order by re.CreatedAt desc ";
        List<Review> reviews = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            if (query.getId() != null) {
                statement.setInt(1, query.getId());
                statement.setInt(2, query.getId());
            } else {
                statement.setNull(1, Types.INTEGER);
                statement.setNull(2, Types.INTEGER);
            }

            if (query.getDoctorId() != null) {
                statement.setInt(3, query.getDoctorId());
                statement.setInt(4, query.getDoctorId());
            } else {
                statement.setNull(3, Types.INTEGER);
                statement.setNull(4, Types.INTEGER);
            }

            if (query.getReviewerId() != null) {
                statement.setInt(5, query.getReviewerId());
                statement.setInt(6, query.getReviewerId());
            } else {
                statement.setNull(5, Types.INTEGER);
                statement.setNull(6, Types.INTEGER);
            }

            if (query.getReviewDoctor() != null) {
                statement.setBoolean(7, query.getReviewDoctor());
                statement.setBoolean(8, query.getReviewDoctor());
                statement.setBoolean(9, query.getReviewDoctor());
            } else {
                statement.setNull(7, Types.BOOLEAN);
                statement.setNull(8, Types.BOOLEAN);
                statement.setNull(9, Types.BOOLEAN);
            }

            if (query.getAppointmentId() != null) {
                statement.setInt(10, query.getAppointmentId());
                statement.setInt(11, query.getAppointmentId());

            } else {
                statement.setNull(10, Types.INTEGER);
                statement.setNull(11, Types.INTEGER);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("Id"));
                review.setContent(resultSet.getString("Content"));
                review.setScore(resultSet.getInt("Score"));
                review.setCreatedAt(resultSet.getTimestamp("CreatedAt") != null
                        ? resultSet.getTimestamp("CreatedAt").toLocalDateTime()
                        : null);
                review.setUpdatedAt(resultSet.getTimestamp("UpdatedAt") != null
                        ? resultSet.getTimestamp("UpdatedAt").toLocalDateTime()
                        : null);

                // doctor
                int doctorId = resultSet.getInt("DoctorId");
                if (doctorId > 0) {
                    Account doctor = new Account();
                    doctor.setId(doctorId);
                    doctor.setAvatar(resultSet.getString("DoctorAvatar"));
                    doctor.setLastName(resultSet.getString("DoctorLastName"));
                    doctor.setFirstName(resultSet.getString("DoctorFirstName"));
                    doctor.setEmail(resultSet.getString("DoctorEmail"));

                    review.setDoctor(doctor);
                }

                int apId = resultSet.getInt("AppointmentId");
                if (apId > 0) {
                    Appointment appointment = new Appointment();
                    appointment.setId(apId);
                    review.setAppointment(appointment);
                }

                // reviewer
                int reviewerId = resultSet.getInt("ReviewerId");
                if (reviewerId > 0) {
                    Account reviewer = new Account();
                    reviewer.setId(reviewerId);
                    reviewer.setAvatar(resultSet.getString("ReviewerAvatar"));
                    reviewer.setLastName(resultSet.getString("ReviewerLastName"));
                    reviewer.setFirstName(resultSet.getString("ReviewerFirstName"));
                    reviewer.setEmail(resultSet.getString("ReviewerEmail"));

                    review.setReviewer(reviewer);
                }

                reviews.add(review);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return reviews;
    }

    public boolean insert(Review review) {
        String sql = "insert into Review (Content, Score, DoctorId, ReviewerId, AppointmentId, CreatedAt, UpdatedAt)" +
                "values (?,?,?,?,?,?,?);";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, review.getContent());
            statement.setInt(2, review.getScore());
            if (review.getDoctor() != null) {
                statement.setInt(3, review.getDoctor().getId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }

            if (review.getReviewer() != null) {
                statement.setInt(4, review.getReviewer().getId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            if (review.getAppointment() != null) {
                statement.setInt(5, review.getAppointment().getId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }

            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

            if (statement.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public List<Review> getTopReview(int top) {
        String sql =
                "select re.*, " +
                        "r.Id as ReviewerId, " +
                        "r.LastName as ReviewerLastName, " +
                        "r.FirstName as ReviewerFirstName, " +
                        "r.Avatar as ReviewerAvatar, " +
                        "r.Email as ReviewerEmail " +
                        "from Review re " +
                        "   left join Account d on d.Id = re.DoctorId " +
                        "   left join Account r on r.Id = re.ReviewerId  " +
                        "where re.DoctorId is null " +
                        "order by re.Score desc " +
                        "offset 0 rows fetch next ? rows only";

        List<Review> reviews = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, top);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("Id"));
                review.setContent(resultSet.getString("Content"));
                review.setScore(resultSet.getInt("Score"));
                review.setCreatedAt(resultSet.getTimestamp("CreatedAt") != null
                        ? resultSet.getTimestamp("CreatedAt").toLocalDateTime()
                        : null);
                review.setUpdatedAt(resultSet.getTimestamp("UpdatedAt") != null
                        ? resultSet.getTimestamp("UpdatedAt").toLocalDateTime()
                        : null);

                // reviewer
                int reviewerId = resultSet.getInt("ReviewerId");
                if (reviewerId > 0) {
                    Account reviewer = new Account();
                    reviewer.setId(reviewerId);
                    reviewer.setAvatar(resultSet.getString("ReviewerAvatar"));
                    reviewer.setLastName(resultSet.getString("ReviewerLastName"));
                    reviewer.setFirstName(resultSet.getString("ReviewerFirstName"));
                    reviewer.setEmail(resultSet.getString("ReviewerEmail"));

                    review.setReviewer(reviewer);
                }

                reviews.add(review);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return reviews;
    }

    public List<Review> getDoctorReview(int doctorId) {
        ReviewQuery reviewQuery = new ReviewQuery();
        reviewQuery.setDoctorId(doctorId);
        return getAll(reviewQuery);
    }

    public Review getHospitalReview(int accountId) {
        ReviewQuery reviewQuery = new ReviewQuery();
        reviewQuery.setReviewDoctor(false);
        reviewQuery.setReviewerId(accountId);
        List<Review> reviews = getAll(reviewQuery);
        return !reviews.isEmpty() ? reviews.get(0) : null;
    }

    public List<Review> getHospitalReviews() {
        ReviewQuery reviewQuery = new ReviewQuery();
        reviewQuery.setReviewDoctor(false);
        return getAll(reviewQuery);
    }

    public Review getReview(int reviewerId, int doctorId) {
        ReviewQuery reviewQuery = new ReviewQuery();
        reviewQuery.setDoctorId(doctorId);
        reviewQuery.setReviewerId(reviewerId);
        List<Review> reviews = getAll(reviewQuery);

        return !reviews.isEmpty() ? reviews.get(0) : null;
    }

    public Review getAppointmentReview(int apId) {
        ReviewQuery reviewQuery = new ReviewQuery();
        reviewQuery.setAppointmentId(apId);
        List<Review> reviews = getAll(reviewQuery);

        return !reviews.isEmpty() ? reviews.get(0) : null;
    }

    public static void main(String[] args) {
        ReviewDao reviewDao = new ReviewDao();
        Review review = reviewDao.getAppointmentReview(26);
        System.out.println(review);
    }
}
