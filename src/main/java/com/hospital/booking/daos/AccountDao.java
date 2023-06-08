package com.hospital.booking.daos;

import com.hospital.booking.database.DatabaseConnection;
import com.hospital.booking.enums.Gender;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Department;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDao {
    private final String querySql = "select * from Account ";

    public List<Account> getAll(Role role) {
        String sql = querySql + " where ? is null or Role = ? ";
        DepartmentDao departmentDao = new DepartmentDao();
        List<Account> accounts = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            String roleName = role != null ? role.name() : null;
            statement.setString(1, roleName);
            statement.setString(2, roleName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("Id"));
                account.setAvatar(resultSet.getString("Avatar"));
                account.setFirstName(resultSet.getString("FirstName"));
                account.setLastName(resultSet.getString("LastName"));
                account.setGender(Gender.valueOf(resultSet.getString("Gender")));
                account.setPhoneNumber(resultSet.getString("PhoneNumber"));
                account.setEmail(resultSet.getString("Email"));

                Date dob = resultSet.getDate("DOB");
                if (dob != null) {
                    account.setDOB(dob.toLocalDate());
                }

                int departmentId = resultSet.getInt("DepartmentId");
                if (departmentId != 0) {
                    account.setDepartment(departmentDao.getById(departmentId));
                }

                account.setDescription(resultSet.getString("Description"));
                account.setToken(resultSet.getString("Token"));
                account.setRole(Role.valueOf(resultSet.getString("Role")));
                account.setCreatedAt(resultSet.getTimestamp("CreatedAt").toLocalDateTime());
                account.setUpdatedAt(resultSet.getTimestamp("UpdatedAt").toLocalDateTime());
                account.setPassword(resultSet.getString("Password"));

                accounts.add(account);
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

        return accounts;
    }

    public Account getAccountById(int id) {
        String sql = querySql + " where Id = ?";
        DepartmentDao departmentDao = new DepartmentDao();
        Account account = null;
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getInt("Id"));
                account.setAvatar(resultSet.getString("Avatar"));
                account.setFirstName(resultSet.getString("FirstName"));
                account.setLastName(resultSet.getString("LastName"));
                account.setGender(Gender.valueOf(resultSet.getString("Gender")));
                account.setPhoneNumber(resultSet.getString("PhoneNumber"));
                account.setEmail(resultSet.getString("Email"));

                Date dob = resultSet.getDate("DOB");
                if (dob != null) {
                    account.setDOB(dob.toLocalDate());
                }

                int departmentId = resultSet.getInt("DepartmentId");
                if (departmentId != 0) {
                    account.setDepartment(departmentDao.getById(departmentId));
                }

                account.setDescription(resultSet.getString("Description"));
                account.setToken(resultSet.getString("Token"));
                account.setRole(Role.valueOf(resultSet.getString("Role")));
                account.setCreatedAt(resultSet.getTimestamp("CreatedAt").toLocalDateTime());
                account.setUpdatedAt(resultSet.getTimestamp("UpdatedAt").toLocalDateTime());
                account.setPassword(resultSet.getString("Password"));

                return account;
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

        return account;
    }

    public Account getAccountByEmail(String email) {
        String sql = querySql + "where Email = ?";
        DepartmentDao departmentDao = new DepartmentDao();
        Account account = null;
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getInt("Id"));
                account.setAvatar(resultSet.getString("Avatar"));
                account.setFirstName(resultSet.getString("FirstName"));
                account.setLastName(resultSet.getString("LastName"));
                account.setGender(Gender.valueOf(resultSet.getString("Gender")));
                account.setPhoneNumber(resultSet.getString("PhoneNumber"));
                account.setEmail(resultSet.getString("Email"));
                account.setPassword(resultSet.getString("Password"));

                Date dob = resultSet.getDate("DOB");
                if (dob != null) {
                    account.setDOB(dob.toLocalDate());
                }

                int departmentId = resultSet.getInt("DepartmentId");
                if (departmentId != 0) {
                    account.setDepartment(departmentDao.getById(departmentId));
                }

                account.setDescription(resultSet.getString("Description"));
                account.setToken(resultSet.getString("Token"));
                account.setRole(Role.valueOf(resultSet.getString("Role")));
                account.setCreatedAt(resultSet.getTimestamp("CreatedAt") != null
                        ? resultSet.getTimestamp("CreatedAt").toLocalDateTime()
                        : null);
                account.setUpdatedAt(resultSet.getTimestamp("UpdatedAt") != null
                        ? resultSet.getTimestamp("UpdatedAt").toLocalDateTime()
                        : null);

                return account;
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

        return account;
    }


    public boolean insertAccount(Account account) {
        String sql = "insert into Account (Avatar, FirstName, LastName, Gender, DOB, PhoneNumber, Email, Description, Role, Password, Address, DepartmentId, CreatedAt, UpdatedAt) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        DepartmentDao departmentDao = new DepartmentDao();
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account.getAvatar());
            statement.setString(2, account.getFirstName());
            statement.setString(3, account.getLastName());
            statement.setString(4, account.getGender() != null ? account.getGender().name() : null);
            statement.setDate(5, account.getDOB() != null ? Date.valueOf(account.getDOB()) : null);
            statement.setString(6, account.getPhoneNumber());
            statement.setString(7, account.getEmail());
            statement.setString(8, account.getDescription());
            statement.setString(9, account.getRole().name());
            statement.setString(10, account.getPassword());
            statement.setString(11, account.getAddress());

            if (account.getDepartment() != null && account.getDepartment().getId() > 0) {
                statement.setInt(12, account.getDepartment().getId());
            } else {
                statement.setNull(12, Types.INTEGER);
            }

            statement.setTimestamp(13, Timestamp.valueOf(account.getCreatedAt()));
            statement.setTimestamp(14, Timestamp.valueOf(account.getUpdatedAt()));

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

    public boolean update(Account account) {
        String sql = "update Account set Avatar = ?, FirstName = ?, LastName = ?, Gender = ?, DOB = ?, PhoneNumber = ?, Email = ?, Description = ?, Address = ?, DepartmentId = ?, UpdatedAt = ? where Id = ?";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account.getAvatar());
            statement.setString(2, account.getFirstName());
            statement.setString(3, account.getLastName());
            statement.setString(4, account.getGender() != null ? account.getGender().name() : null);
            statement.setDate(5, account.getDOB() != null ? Date.valueOf(account.getDOB()) : null);
            statement.setString(6, account.getPhoneNumber());
            statement.setString(7, account.getEmail());
            statement.setString(8, account.getDescription());
            statement.setString(9, account.getAddress());
            if (account.getDepartment() != null && account.getDepartment().getId() > 0) {
                statement.setInt(10, account.getDepartment().getId());
            } else {
                statement.setNull(10, Types.INTEGER);
            }
            statement.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(12, account.getId());

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

    public boolean updatePassword(int accountId, String password) {
        String sql = "update Account set Password = ? where Id = ?";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setInt(2, accountId);
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
