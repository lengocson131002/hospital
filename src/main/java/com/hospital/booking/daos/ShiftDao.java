package com.hospital.booking.daos;

import com.hospital.booking.database.DatabaseConnection;
import com.hospital.booking.database.ShiftQuery;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.models.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShiftDao {

    public List<Shift> getAll(ShiftQuery query) {
        List<Shift> shifts = new ArrayList<>();
        Connection connection = null;
        String sql =
                "select s.Id, " +
                        "s.DoctorId, " +
                        "s.Date, " +
                        "s.Slot, " +
                        "s.CreatedAt, " +
                        "s.UpdatedAt, " +
                        "acc.Avatar as DoctorAvatar, " +
                        "acc.FirstName as DoctorFirstName, " +
                        "acc.LastName as DoctorLastName, " +
                        "IIF(SUM(IIF(a.Status IS NULL or a.Status = 'CANCELED', 0, 1)) > 0, 0, 1) AS IsAvailable " +
                "from Shift s " +
                "left join Account acc on acc.Id = s.DoctorId " +
                "left join Appointment a on s.Id = a.ShiftId " +
                        "where (? is null or s.Id = ?)" +
                        " and (? is null or s.Date >= ?)" +
                        " and (? is null or s.Date <= ?)" +
                        " and (? is null or s.Slot = ?)" +
                        " and (? is null or s.DoctorId = ?) " +
                        " and (? is null or (? = 1 and acc.IsActive = 1) or (? = 0 and acc.IsActive = 0)) " +
                "group by s.Id, " +
                        "s.DoctorId, " +
                        "s.Date, " +
                        "s.Slot, " +
                        "s.CreatedAt, " +
                        "s.UpdatedAt, " +
                        "acc.Avatar," +
                        "acc.FirstName, " +
                        "acc.LastName " +
                "having ? is null " +
                        "or (? = 0 and IIF(SUM(IIF(a.Status IS NULL or a.Status = 'CANCELED', 0, 1)) > 0, 0, 1) = 0) " +
                        "or (? = 1 and IIF(SUM(IIF(a.Status IS NULL or a.Status = 'CANCELED', 0, 1)) > 0, 0, 1) = 1)";

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

            Date sqlFrom = query.getFrom() != null ? Date.valueOf(query.getFrom()) : null;
            statement.setDate(3, sqlFrom);
            statement.setDate(4, sqlFrom);

            Date sqlTo = query.getTo() != null ? Date.valueOf(query.getTo()) : null;
            statement.setDate(5, sqlTo);
            statement.setDate(6, sqlTo);

            if (query.getSlot() != null) {
                statement.setInt(7, query.getSlot());
                statement.setInt(8, query.getSlot());
            } else  {
                statement.setNull(7, Types.INTEGER);
                statement.setNull(8, Types.INTEGER);
            }

            if (query.getDoctorId() != null) {
                statement.setInt(9, query.getDoctorId());
                statement.setInt(10, query.getDoctorId());
            } else  {
                statement.setNull(9, Types.INTEGER);
                statement.setNull(10, Types.INTEGER);
            }

            if (query.getAvailable() != null) {
                statement.setBoolean(11, query.getAvailable());
                statement.setBoolean(12, query.getAvailable());
                statement.setBoolean(13, query.getAvailable());
                statement.setBoolean(14, query.getAvailable());
                statement.setBoolean(15, query.getAvailable());
                statement.setBoolean(16, query.getAvailable());
            } else {
                statement.setNull(11, Types.BOOLEAN);
                statement.setNull(12, Types.BOOLEAN);
                statement.setNull(13, Types.BOOLEAN);
                statement.setNull(14, Types.BOOLEAN);
                statement.setNull(15, Types.BOOLEAN);
                statement.setNull(16, Types.BOOLEAN);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Shift shift = new Shift();
                shift.setId(resultSet.getInt("Id"));

                Account doctor = new Account();
                doctor.setId(resultSet.getInt("DoctorId"));
                doctor.setAvatar(resultSet.getString("DoctorAvatar"));
                doctor.setFirstName(resultSet.getString("DoctorFirstName"));
                doctor.setLastName(resultSet.getString("DoctorLastName"));

                shift.setDoctor(doctor);

                shift.setDate(resultSet.getDate("Date").toLocalDate());
                shift.setSlot(resultSet.getInt("Slot"));

                shift.setCreatedAt(resultSet.getTimestamp("CreatedAt") != null
                        ? resultSet.getTimestamp("CreatedAt").toLocalDateTime()
                        : null);

                shift.setCreatedAt(resultSet.getTimestamp("UpdatedAt") != null
                        ? resultSet.getTimestamp("UpdatedAt").toLocalDateTime()
                        : null);

                boolean booked = resultSet.getInt("IsAvailable") == 0;
                shift.setBooked(booked);
                shifts.add(shift);
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

        return shifts;
    }

    public List<Shift> getAll(LocalDate date, Boolean isAvailable) {
        ShiftQuery query = new ShiftQuery();
        query.setFrom(date);
        query.setTo(date);
        query.setAvailable(isAvailable);
        return getAll(query);
    }

    public List<Shift> getAll(LocalDate date) {
        return getAll(date, null);
    }

    public List<Shift> getAll(int doctorId,  LocalDate from, LocalDate to, Boolean isAvailable) {
        ShiftQuery query = new ShiftQuery();
        query.setDoctorId(doctorId);
        query.setFrom(from);
        query.setTo(to);
        query.setAvailable(isAvailable);
        return getAll(query);
    }

    public List<Shift> getAll(int doctorId,  LocalDate from, LocalDate to) {
        ShiftQuery query = new ShiftQuery();
        query.setDoctorId(doctorId);
        query.setFrom(from);
        query.setTo(to);
        return getAll(query);
    }

    public List<Shift> getAll(int doctorId,  LocalDate date, Boolean isAvailable) {
        ShiftQuery query = new ShiftQuery();
        query.setDoctorId(doctorId);
        query.setFrom(date);
        query.setTo(date);
        query.setAvailable(isAvailable);
        return getAll(query);
    }

    public List<Shift> getAll(int doctorId,  LocalDate date) {
        ShiftQuery query = new ShiftQuery();
        query.setDoctorId(doctorId);
        query.setFrom(date);
        query.setTo(date);
        return getAll(query);
    }

    public List<Shift> getAll() {
        ShiftQuery query = new ShiftQuery();
        return getAll(query);
    }

    public Shift getById(int id) {
        ShiftQuery query = new ShiftQuery();
        query.setId(id);
        List<Shift> shifts = getAll(query);
        return !shifts.isEmpty() ? shifts.get(0) : null;
    }

    public Shift get(int doctorId, LocalDate date, int slot) {
        ShiftQuery query = new ShiftQuery();
        query.setDoctorId(doctorId);
        query.setFrom(date);
        query.setTo(date);
        query.setSlot(slot);
        List<Shift> shifts = getAll(query);
        return !shifts.isEmpty() ? shifts.get(0) : null;
    }

    public boolean insert(Shift shift) {
        String sql = "insert into Shift (DoctorId, Date, Slot, CreatedAt, UpdatedAt) values (?,?,?,?,?)";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, shift.getDoctor().getId());
            statement.setDate(2, Date.valueOf(shift.getDate()));
            statement.setInt(3, shift.getSlot());
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));


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

    public boolean delete(int id) {
        String sql = "delete from Shift where id = ?";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
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

    public static void main(String[] args) {
        for(Shift shift : new ShiftDao().getAll(LocalDate.now().plusDays(1), true)) {
            System.out.println(shift);
        }
    }
}
