package com.hospital.booking.controllers.patient;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/patient/appointments")
public class AppointmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Account account = (Account) session.getAttribute(SessionConstants.ACCOUNT);

        AppointmentDao appointmentDao = new AppointmentDao();
        List<Appointment> appointments = appointmentDao.getAllByBooker(account.getId());

        req.setAttribute("appointments", appointments);
        req.getRequestDispatcher("/patient/appointments.jsp").forward(req, resp);
    }

}
