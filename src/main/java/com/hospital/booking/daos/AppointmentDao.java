package com.hospital.booking.daos;

import com.hospital.booking.database.AppointmentQuery;
import com.hospital.booking.database.DatabaseConnection;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Department;
import com.hospital.booking.models.Shift;
import com.microsoft.sqlserver.jdbc.StringUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDao {
    public boolean insertAppointment(Appointment appointment) {
        if (appointment.getBooker() == null || appointment.getDoctor() == null || appointment.getShift() == null) {
            Logger.getLogger(AppointmentDao.class.getName()).log(Level.SEVERE, "Null booker || doctor || shift");
            return false;
        }
        String sql = "insert into Appointment(BookerId, DoctorId, ShiftId, Status, PatientName, PatientPhone, PatientEmail, PatientNote, CreatedAt, UpdatedAt) values (?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, appointment.getBooker().getId());
            statement.setInt(2, appointment.getDoctor().getId());
            statement.setInt(3, appointment.getShift().getId());
            statement.setString(4, appointment.getStatus() != null ? appointment.getStatus().name() : null);
            statement.setString(5, appointment.getPatientName());
            statement.setString(6, appointment.getPatientPhoneNumber());
            statement.setString(7, appointment.getPatientEmail());
            statement.setString(8, appointment.getPatientNote());
            statement.setTimestamp(9, Timestamp.valueOf(appointment.getCreatedAt()));
            statement.setTimestamp(10, Timestamp.valueOf(appointment.getUpdatedAt()));

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

    public boolean update(Appointment appointment) {
        String sql = "update Appointment " +
                "set BookerId = ?, " +
                "    DoctorId = ?, " +
                "    ShiftId = ?, " +
                "    Status = ?, " +
                "    PatientName = ?, " +
                "    PatientPhone = ?, " +
                "    PatientEmail = ?, " +
                "    PatientNote = ?, " +
                "    DoctorNote = ?, " +
                "    UpdatedAt = ?, " +
                "    BillId = ? " +
                "where Id = ?";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, appointment.getBooker().getId());
            statement.setInt(2, appointment.getDoctor().getId());
            statement.setInt(3, appointment.getShift().getId());
            statement.setString(4, appointment.getStatus() != null ? appointment.getStatus().name() : null);
            statement.setString(5, appointment.getPatientName());
            statement.setString(6, appointment.getPatientPhoneNumber());
            statement.setString(7, appointment.getPatientEmail());
            statement.setString(8, appointment.getPatientNote());
            statement.setString(9, appointment.getDoctorNote());
            statement.setTimestamp(10, Timestamp.valueOf(appointment.getUpdatedAt()));
            if (appointment.getBill() != null) {
                statement.setInt(11, appointment.getBill().getId());
            } else {
                statement.setNull(11, Types.INTEGER);
            }

            statement.setInt(12, appointment.getId());


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

    public List<Appointment> getAll(AppointmentQuery query) {
        if (query == null) {
            query = new AppointmentQuery();
        }
        List<Appointment> appointments = new ArrayList<>();
        Connection connection = null;
        String sql =
                "select  a.Id, a.DoctorId, a.BookerId, a.BillId, a.Status, a.PatientName, a.PatientPhone, a.PatientEmail, a.PatientNote, a.DoctorNote, a.CreatedAt, a.UpdatedAt, s.Id as ShiftId, s.Date, s.Slot " +
                        "from Appointment a " +
                        "left join Shift s on s.Id = a.ShiftId " +
                        "where (? is null or a.Id = ?) " +
                        "and (? is null or a.DoctorId = ?) " +
                        "and (? is null or a.BookerId = ?) " +
                        "and (? is null or s.Date >= ?) " +
                        "and (? is null or s.Date <= ?) " +
                        "and (? is null or s.Slot = ?) " +
                        "and (? is null or a.Status = ?) " +
                        "and (? is null or a.PatientName like ? or a.PatientEmail like ? or a.PatientPhone like ?) " +
                        "and (? is null or a.BillId = ?) " +
                        "order by s.Date desc, s.Slot asc";

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

            if (query.getBookerId() != null) {
                statement.setInt(5, query.getBookerId());
                statement.setInt(6, query.getBookerId());
            } else {
                statement.setNull(5, Types.INTEGER);
                statement.setNull(6, Types.INTEGER);
            }

            Date sqlFrom = query.getFrom() != null ? Date.valueOf(query.getFrom()) : null;
            statement.setDate(7, sqlFrom);
            statement.setDate(8, sqlFrom);

            Date sqlTo = query.getTo() != null ? Date.valueOf(query.getTo()) : null;
            statement.setDate(9, sqlTo);
            statement.setDate(10, sqlTo);

            if (query.getSlot() != null) {
                statement.setInt(11, query.getSlot());
                statement.setInt(12, query.getSlot());
            } else {
                statement.setNull(11, Types.INTEGER);
                statement.setNull(12, Types.INTEGER);
            }

            String sqlStatus = query.getStatus() != null ? query.getStatus().name() : null;
            statement.setString(13, sqlStatus);
            statement.setString(14, sqlStatus);

            String q = !StringUtils.isEmpty(query.getQuery()) ? "%" + query.getQuery().trim() + "%" : null;
            statement.setString(15, q);
            statement.setString(16, q);
            statement.setString(17, q);
            statement.setString(18, q);

            if (query.getBillId() != null) {
                statement.setInt(19, query.getBillId());
                statement.setInt(20, query.getBillId());
            } else {
                statement.setNull(19, Types.INTEGER);
                statement.setNull(20, Types.INTEGER);
            }

            ResultSet resultSet = statement.executeQuery();
            AccountDao accountDao = new AccountDao();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("Id"));
                appointment.setDoctor(accountDao.getAccountById(resultSet.getInt("DoctorId")));
                appointment.setBooker(accountDao.getAccountById(resultSet.getInt("BookerId")));
                appointment.setStatus(AppointmentStatus.valueOf(resultSet.getString("Status")));
                appointment.setPatientName(resultSet.getString("PatientName"));
                appointment.setPatientEmail(resultSet.getString("PatientEmail"));
                appointment.setPatientPhoneNumber(resultSet.getString("PatientPhone"));
                appointment.setPatientNote(resultSet.getString("PatientNote"));
                appointment.setDoctorNote(resultSet.getString("DoctorNote"));
                appointment.setCreatedAt(resultSet.getTimestamp("CreatedAt") != null
                        ? resultSet.getTimestamp("CreatedAt").toLocalDateTime()
                        : null);
                appointment.setUpdatedAt(resultSet.getTimestamp("UpdatedAt") != null
                        ? resultSet.getTimestamp("UpdatedAt").toLocalDateTime()
                        : null);

                Shift shift = new Shift();
                shift.setId(resultSet.getInt("ShiftId"));
                shift.setDate(resultSet.getDate("Date").toLocalDate());
                shift.setSlot(resultSet.getInt("Slot"));
                appointment.setShift(shift);

                appointments.add(appointment);
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

        return appointments;
    }

    public List<Appointment> getAllByBooker(int bookerId, LocalDate from, LocalDate to) {
        AppointmentQuery query = new AppointmentQuery();
        query.setBookerId(bookerId);
        query.setFrom(from);
        query.setTo(to);
        return getAll(query);
    }

    public List<Appointment> getAllByBooker(int bookerId) {
        AppointmentQuery query = new AppointmentQuery();
        query.setBookerId(bookerId);
        return getAll(query);
    }

    public List<Appointment> getAllByDoctor(int doctorId, LocalDate from, LocalDate to) {
        AppointmentQuery query = new AppointmentQuery();
        query.setDoctorId(doctorId);
        query.setFrom(from);
        query.setTo(to);
        return getAll(query);
    }

    public List<Appointment> getAllByDoctor(int doctorId) {
        AppointmentQuery query = new AppointmentQuery();
        query.setDoctorId(doctorId);
        return getAll(query);
    }

    public List<Appointment> getAll() {
        return getAll(null);
    }

    public Appointment getById(int id) {
        AppointmentQuery query = new AppointmentQuery();
        query.setId(id);
        List<Appointment> appointments = getAll(query);
        return !appointments.isEmpty() ? appointments.get(0) : null;
    }

    public Appointment getByBillId(int billId) {
        AppointmentQuery query = new AppointmentQuery();
        query.setBillId(billId);
        List<Appointment> appointments = getAll(query);
        return !appointments.isEmpty() ? appointments.get(0) : null;
    }

}
