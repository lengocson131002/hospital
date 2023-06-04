package com.hospital.booking.daos;

import com.hospital.booking.database.DatabaseConnection;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDao {
    public boolean insertAppointment(Appointment appointment) {
        String sql = "insert into Appointment (PatientName, PatientPhone, PatientEmail, Status, PatientNote, BookerId, DoctorId) values (?,?,?,?,?,?,?);";
        DepartmentDao departmentDao = new DepartmentDao();
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, appointment.getPatientName());
            statement.setString(2, appointment.getPatientPhoneNumber());
            statement.setString(3, appointment.getPatientEmail());
            statement.setString(4, appointment.getStatus() != null ? appointment.getStatus().name() : null);
            statement.setString(5, appointment.getPatientNote());
            statement.setInt(6, appointment.getBooker().getId());
            statement.setInt(7, appointment.getDoctor().getId());

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
