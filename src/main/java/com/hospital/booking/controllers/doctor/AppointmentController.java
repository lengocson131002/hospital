package com.hospital.booking.controllers.doctor;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.database.AppointmentQuery;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.utils.DatetimeUtils;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/doctor/appointments")
public class AppointmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute(SessionConstants.ACCOUNT);

        AppointmentQuery query = new AppointmentQuery();
        query.setDoctorId(account.getId());


        String from = req.getParameter("from");
        if (!StringUtils.isEmpty(from)) {
            LocalDate fromDate = DatetimeUtils.toDate(from, "yyyy-MM-dd");
            query.setFrom(fromDate);
            req.setAttribute("from", fromDate);
        }

        String to = req.getParameter("to");
        if (!StringUtils.isEmpty(to)) {
            LocalDate toDate = DatetimeUtils.toDate(to, "yyyy-MM-dd");
            query.setTo(toDate);
            req.setAttribute("to", toDate);
        }

        String status = req.getParameter("status");
        if (!StringUtils.isEmpty(status)) {
            query.setStatus(AppointmentStatus.valueOf(status));
            req.setAttribute("status", status);
        }

        AppointmentDao appointmentDao = new AppointmentDao();
        List<Appointment> appointments = appointmentDao.getAll(query);

        req.setAttribute("appointments", appointments);
        req.getRequestDispatcher("/doctor/appointments.jsp").forward(req, resp);
    }

}
