package com.hospital.booking.controllers.patient;

import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.models.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/patient/appointment")
public class AppointmentDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getById(id);

        if (appointment == null) {
            req.setAttribute("error", "Không tìm thấy lịch hẹn yêu cầu");
            req.getRequestDispatcher("/patient/appointments.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("appointment", appointment);
        req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
    }

}
