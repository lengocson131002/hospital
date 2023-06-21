package com.hospital.booking.daos;

import com.hospital.booking.database.AccountQuery;
import com.hospital.booking.database.DatabaseConnection;
import com.hospital.booking.enums.Gender;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Department;
import com.microsoft.sqlserver.jdbc.StringUtils;
import sun.management.counter.AbstractCounter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDao {
    private final String querySql = "select a.* from Account a";

    public static void main(String[] args) {
        AccountDao dao = new AccountDao();
        List<Account> tops = dao.getTopDoctors(5, true);
        System.out.println("Size: "+ tops.size());
        for (Account acc : tops) {
            System.out.println(acc);
        }
    }

    public List<Account> getAll(AccountQuery query) {
        String sql = "select a.*, d.Id as DepartmentId, d.Name as DepartmentName, d.Description as DepartmentDescription " +
                "from Account a " +
                "   left join Department d on d.Id = a.DepartmentId " +
                "   where (? is null or Role = ?) " +
                "   and (? is null or Email = ?) " +
                "   and (? is null or IsActive = ?) " +
                "   and (? is null or a.Id = ?) " +
                "   and (? is null or FirstName like ? or LastName like ? or PhoneNumber like ? or Email like ?) " +
                "   and (? is null or a.DepartmentId = ?)" +
                "order by a.CreatedAt desc ";
        List<Account> accounts = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            String sqlRole = query.getRole() != null
                    ? query.getRole().name()
                    : null;

            statement.setString(1, sqlRole);
            statement.setString(2, sqlRole);

            statement.setString(3, query.getEmail());
            statement.setString(4, query.getEmail());

            if (query.getActive() != null) {
                statement.setBoolean(5, query.getActive());
                statement.setBoolean(6, query.getActive());
            } else {
                statement.setNull(5, Types.BOOLEAN);
                statement.setNull(6, Types.BOOLEAN);
            }

            if (query.getId() != null) {
                statement.setInt(7, query.getId());
                statement.setInt(8, query.getId());
            } else {
                statement.setNull(7, Types.INTEGER);
                statement.setNull(8, Types.INTEGER);
            }

            String q = !StringUtils.isEmpty(query.getSearch()) ? "%" + query.getSearch().trim() + "%" : null;
            statement.setString(9, q);
            statement.setString(10, q);
            statement.setString(11, q);
            statement.setString(12, q);
            statement.setString(13, q);

            if (query.getDepartmentId() != null) {
                statement.setInt(14, query.getDepartmentId());
                statement.setInt(15, query.getDepartmentId());
            } else {
                statement.setNull(14, Types.INTEGER);
                statement.setNull(15, Types.INTEGER);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("Id"));
                account.setAvatar(resultSet.getString("Avatar"));
                account.setFirstName(resultSet.getString("FirstName"));
                account.setLastName(resultSet.getString("LastName"));
                account.setGender(resultSet.getString("Gender") != null
                        ? Gender.valueOf(resultSet.getString("Gender"))
                        : null);
                account.setPhoneNumber(resultSet.getString("PhoneNumber"));
                account.setEmail(resultSet.getString("Email"));

                Date dob = resultSet.getDate("DOB");
                if (dob != null) {
                    account.setDOB(dob.toLocalDate());
                }

                int departmentId = resultSet.getInt("DepartmentId");
                if (departmentId != 0) {
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(resultSet.getString("DepartmentName"));
                    department.setDescription(resultSet.getString("DepartmentDescription"));

                    account.setDepartment(department);
                }

                account.setDescription(resultSet.getString("Description"));
                account.setToken(resultSet.getString("Token"));
                account.setRole(Role.valueOf(resultSet.getString("Role")));
                account.setCreatedAt(resultSet.getTimestamp("CreatedAt").toLocalDateTime());
                account.setUpdatedAt(resultSet.getTimestamp("UpdatedAt").toLocalDateTime());
                account.setPassword(resultSet.getString("Password"));
                account.setActive(resultSet.getBoolean("IsActive"));
                account.setAddress(resultSet.getString("Address"));
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

    public List<Account> getAll(Role role) {
        AccountQuery query = new AccountQuery();
        query.setRole(role);
        return getAll(query);
    }

    public Account getAccountById(int id) {
        AccountQuery query = new AccountQuery();
        query.setId(id);
        List<Account> accounts = getAll(query);
        return !accounts.isEmpty()
                ? accounts.get(0)
                : null;
    }

    public Account getAccountByEmail(String email) {
        AccountQuery query = new AccountQuery();
        query.setEmail(email);
        List<Account> accounts = getAll(query);
        return !accounts.isEmpty()
                ? accounts.get(0)
                : null;
    }

    public boolean insertAccount(Account account) {
        String sql = "insert into Account (Avatar, FirstName, LastName, Gender, DOB, PhoneNumber, Email, Description, Role, Password, Address, DepartmentId, CreatedAt, UpdatedAt, IsActive) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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

            statement.setBoolean(15, account.isActive());

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
        String sql = "update Account " +
                "set Avatar = ?, " +
                "FirstName = ?, " +
                "LastName = ?, " +
                "Gender = ?, " +
                "DOB = ?, " +
                "PhoneNumber = ?, " +
                "Email = ?, " +
                "Description = ?, " +
                "Address = ?, " +
                "DepartmentId = ?, " +
                "UpdatedAt = ? , " +
                "IsActive = ? " +
                "where Id = ?";
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
            statement.setBoolean(12, account.isActive());
            statement.setInt(13, account.getId());

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

    public List<Account> getTopDoctors(int top) {
        return getTopDoctors(top, true);
    }

    public List<Account> getTopDoctors(int top,Boolean byAppointmentCount) {
        String sql = "select Top(?) " +
                "   a.Id, " +
                "   a.Avatar, " +
                "   a.FirstName, " +
                "   a.LastName, " +
                "   a.Email, " +
                "   a.PhoneNumber, " +
                "   a.Gender, " +
                "   d.Id as DepartmentId, " +
                "   d.Name as DepartmentName, " +
                "   d.Description as DepartmentDescription, " +
                "   COUNT(ap.id) as AppointmentCount " +
                "from Account a " +
                "   left join Department d on d.Id = a.DepartmentId " +
                "   left join Appointment ap on ap.DoctorId = a.Id " +
                "where a.Role = 'DOCTOR' and (? is null or ? = 0 or (? = 1 and ap.id is not null and ap.status = 'COMPLETED')) " +
                "group by a.Id, " +
                "   a.Avatar, " +
                "   a.FirstName, " +
                "   a.LastName, " +
                "   a.Email, " +
                "   a.PhoneNumber, " +
                "   a.Gender, " +
                "   d.Id," +
                "   d.Name, " +
                "   d.Description " +
                "order by AppointmentCount desc ";
        List<Account> accounts = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, top);
            if (byAppointmentCount != null) {
                statement.setBoolean(2, byAppointmentCount);
                statement.setBoolean(3, byAppointmentCount);
                statement.setBoolean(4, byAppointmentCount);
            } else {
                statement.setNull(2, Types.BOOLEAN);
                statement.setNull(3, Types.BOOLEAN);
                statement.setNull(4, Types.BOOLEAN);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("Id"));
                account.setAvatar(resultSet.getString("Avatar"));
                account.setFirstName(resultSet.getString("FirstName"));
                account.setLastName(resultSet.getString("LastName"));
                account.setGender(resultSet.getString("Gender") != null
                        ? Gender.valueOf(resultSet.getString("Gender"))
                        : null);
                account.setPhoneNumber(resultSet.getString("PhoneNumber"));
                account.setEmail(resultSet.getString("Email"));


                int departmentId = resultSet.getInt("DepartmentId");
                if (departmentId != 0) {
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(resultSet.getString("DepartmentName"));
                    department.setDescription(resultSet.getString("DepartmentDescription"));

                    account.setDepartment(department);
                }
                account.setAppointmentCount(resultSet.getInt("AppointmentCount"));
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

}
