package com.hospital.booking.daos;

import com.hospital.booking.database.DatabaseConnection;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.enums.BillStatus;
import com.hospital.booking.models.*;
import com.microsoft.sqlserver.jdbc.StringUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillDao {

    public int insert(Bill bill) {
        String sql = "insert into Bill (Price, PatientName, PatientPhone, PatientEmail, Note, CreatedAt, UpdatedAt, Status)" +
                "values (?,?,?,?,?,?,?,?);";
        DepartmentDao departmentDao = new DepartmentDao();
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, bill.getPrice());
            statement.setString(2, bill.getPatientName());
            statement.setString(3, bill.getPatientPhoneNumber());
            statement.setString(4, bill.getPatientEmail());
            statement.setString(5, bill.getNote());
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(8, BillStatus.CREATED.name());

            if (statement.executeUpdate() < 0) {
                throw new SQLException("Create bill failed");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
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
        return 0;
    }

    public List<Bill> getAll(LocalDateTime from, LocalDateTime to, BillStatus status, String query, Integer id) {
        List<Bill> bills = new ArrayList<>();
        Connection connection = null;
        String sql =
                "select  b.Id, b.PatientName, b.PatientPhone, b.PatientEmail, b.Price, b.Status, b.Note, b.CheckoutAt, b.CreatedAt, b.UpdatedAt " +
                        "from Bill b " +
                        "where (? is null or b.Id = ?) " +
                        "and (? is null or b.CreatedAt >= ?) " +
                        "and (? is null or b.CreatedAt <= ?) " +
                        "and (? is null or b.Status = ?) " +
                        "and (? is null or b.PatientName like ? or b.PatientEmail like ? or b.PatientPhone like ?) " +
                        "order by b.CreatedAt desc";

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

            Timestamp sqlFrom = from != null ? Timestamp.valueOf(from) : null;
            statement.setTimestamp(3, sqlFrom);
            statement.setTimestamp(4, sqlFrom);

            Timestamp sqlTo = to != null ? Timestamp.valueOf(to) : null;
            statement.setTimestamp(5, sqlTo);
            statement.setTimestamp(6, sqlTo);

            String sqlStatus = status != null ? status.name() : null;
            statement.setString(7, sqlStatus);
            statement.setString(8, sqlStatus);

            query = !StringUtils.isEmpty(query) ? "%" + query.trim() + "%" : null;
            statement.setString(9, query);
            statement.setString(10, query);
            statement.setString(11, query);
            statement.setString(12, query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setId(resultSet.getInt("Id"));
                bill.setPrice(resultSet.getDouble("Price"));

                String s = resultSet.getString("Status");
                bill.setStatus(!StringUtils.isEmpty(s) ? BillStatus.valueOf(s) : null);

                bill.setPatientName(resultSet.getString("PatientName"));
                bill.setPatientEmail(resultSet.getString("PatientEmail"));
                bill.setPatientPhoneNumber(resultSet.getString("PatientPhone"));

                Timestamp checkoutAt = resultSet.getTimestamp("CheckoutAt");
                bill.setCheckoutAt(checkoutAt != null ? checkoutAt.toLocalDateTime() : null);

                Timestamp createdAt = resultSet.getTimestamp("CreatedAt");
                bill.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);

                Timestamp updatedAt = resultSet.getTimestamp("UpdatedAt");
                bill.setUpdatedAt(updatedAt != null ? updatedAt.toLocalDateTime() : null);

                bill.setNote(resultSet.getString("Note"));
                bills.add(bill);
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

        return bills;
    }

    public List<Bill> getAll() {
        return getAll(null, null, null, null, null);
    }

    public List<Bill> getAll(BillStatus status, String query) {
        return getAll(null, null, status, query, null);
    }


    public List<Bill> getAll(LocalDateTime from, LocalDateTime to) {
        return getAll(from, to, null, null, null);
    }

    public Bill getById(int id) {
        List<Bill> bills = getAll(null, null, null, null, id);
        return !bills.isEmpty() ? bills.get(0) : null;
    }

    public boolean update(Bill bill) {
        String sql = "update Bill " +
                "set Note = ?, " +
                "    CheckoutAt = ?, " +
                "    UpdatedAt = ?, " +
                "    Status = ? " +
                "where Id = ?";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bill.getNote());
            statement.setTimestamp(2, Timestamp.valueOf(bill.getCheckoutAt()));
            statement.setTimestamp(3, Timestamp.valueOf(bill.getUpdatedAt()));
            statement.setString(4, bill.getStatus() != null ? bill.getStatus().name() : null);
            statement.setInt(5, bill.getId());

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
}
