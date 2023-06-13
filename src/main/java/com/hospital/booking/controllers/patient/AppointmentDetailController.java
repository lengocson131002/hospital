package com.hospital.booking.controllers.patient;

import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.daos.ReviewDao;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Review;

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

        ReviewDao reviewDao = new ReviewDao();
        Review review = reviewDao.getAppointmentReview(appointment.getId());
        if (review != null) {
            req.setAttribute("review", review);
        }

        req.setAttribute("appointment", appointment);
        req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
    }

}
