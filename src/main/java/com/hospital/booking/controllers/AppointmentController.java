package com.hospital.booking.controllers;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.enums.Gender;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Appointment;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@WebServlet("/appointment")
public class AppointmentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("appointment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Appointment appointment = new Appointment();
        AccountDao accountDao = new AccountDao();
        AppointmentDao appointmentDao = new AppointmentDao();
        appointment.setDoctor(accountDao.getAccountById(3));
        appointment.setBooker(accountDao.getAccountById(1));
        appointment.setPatientName(req.getParameter("name"));
        appointment.setPatientEmail(req.getParameter("email"));
        appointment.setPatientPhoneNumber(req.getParameter("phoneNumber"));
        appointment.setPatientNote(req.getParameter("note"));
        appointment.setStatus(AppointmentStatus.CREATED);
        appointmentDao.insertAppointment(appointment);
//        List<Account> accounts = accountDao.getAll(Role.DOCTOR);
//
//
//        if (accounts != null) {
//            req.setAttribute("accounts", accounts);
//            req.getRequestDispatcher("add-staff.jsp").forward(req, resp);
//        }
    }
}
