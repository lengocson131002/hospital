package com.hospital.booking.daos;

import com.hospital.booking.database.DatabaseConnection;
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

    public List<Shift> getAll(Integer id, LocalDate from, LocalDate to,  Integer slot, Integer doctorId, Boolean isAvailable) {
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

            if (id != null) {
                statement.setInt(1, id);
                statement.setInt(2, id);
            } else {
                statement.setNull(1, Types.INTEGER);
                statement.setNull(2, Types.INTEGER);
            }

            Date sqlFrom = from != null ? Date.valueOf(from) : null;
            statement.setDate(3, sqlFrom);
            statement.setDate(4, sqlFrom);

            Date sqlTo = to != null ? Date.valueOf(to) : null;
            statement.setDate(5, sqlTo);
            statement.setDate(6, sqlTo);

            if (slot != null) {
                statement.setInt(7, slot);
                statement.setInt(8, slot);
            } else  {
                statement.setNull(7, Types.INTEGER);
                statement.setNull(8, Types.INTEGER);
            }

            if (doctorId != null) {
                statement.setInt(9, doctorId);
                statement.setInt(10, doctorId);
            } else  {
                statement.setNull(9, Types.INTEGER);
                statement.setNull(10, Types.INTEGER);
            }

            if (isAvailable != null) {
                statement.setBoolean(11, isAvailable);
                statement.setBoolean(12, isAvailable);
                statement.setBoolean(13, isAvailable);
            } else {
                statement.setNull(11, Types.BOOLEAN);
                statement.setNull(12, Types.BOOLEAN);
                statement.setNull(13, Types.BOOLEAN);
            }

            ResultSet resultSet = statement.executeQuery();
            AccountDao accountDao = new AccountDao();
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
        return getAll(null, date, date, null, null, isAvailable);
    }

    public List<Shift> getAll(LocalDate date) {
        return getAll(date, null);
    }

    public List<Shift> getAll(int doctorId,  LocalDate from, LocalDate to, Boolean isAvailable) {
        return getAll(null, from, to, null, doctorId, isAvailable);
    }

    public List<Shift> getAll(int doctorId,  LocalDate from, LocalDate to) {
        return getAll(doctorId, from, to, null);
    }

    public List<Shift> getAll(int doctorId,  LocalDate date, Boolean isAvailable) {
        return getAll(null, date, date, null, doctorId, isAvailable);
    }

    public List<Shift> getAll(int doctorId,  LocalDate date) {
        return getAll(null, date, date, null, doctorId, null);
    }

    public List<Shift> getAll() {
        return getAll(null, null, null, null, null, null);
    }

    public Shift getById(int id) {
        List<Shift> shifts = getAll(id, null, null, null, null, null);
        return !shifts.isEmpty() ? shifts.get(0) : null;
    }

    public Shift get(int doctorId, LocalDate date, int slot) {
        List<Shift> shifts = getAll(null, date, date, slot, doctorId, null);
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
