package com.hospital.booking.controllers.doctor;

import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.models.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/doctor/appointment")
public class AppointmentDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int id = Integer.parseInt(req.getParameter("id"));
        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getById(id);

        if (appointment == null) {
            session.setAttribute("error", "Không tìm thấy lịch hẹn yêu cầu");
            resp.sendRedirect(req.getContextPath() + "/doctor/appointments");
            return;
        }

        req.setAttribute("appointment", appointment);
        req.getRequestDispatcher("/doctor/appointment.jsp").forward(req, resp);
    }

}
